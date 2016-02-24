package instrument;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import org.apache.log4j.Logger;

public class SymAgent implements ClassFileTransformer {

    private  Logger logger = Logger.getLogger(SymbolicMirror.class);


    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new SymAgent());
        String value = System.getProperty("conf");
        System.out.println(value);
    }

    @Override
    public byte[] transform(ClassLoader loader, String cname, Class<?> cclass, ProtectionDomain d, byte[] cbuf) throws IllegalClassFormatException {
        if (!cname.startsWith("examples")) return cbuf;
        logger.info("transforming: " + cname);

        try {
            ClassReader cr = new ClassReader(cbuf);
            ClassNode cn = new ClassNode();
            cr.accept(cn, 0);

            for (MethodNode mn : (List<MethodNode>) cn.methods) {
                InsnList insns = mn.instructions;
                if (insns.size() == 0) {
                    continue;
                }
                Iterator<AbstractInsnNode> j = insns.iterator();
                while (j.hasNext()) {
                    AbstractInsnNode in = j.next();
                    if (in.getOpcode() == Opcodes.ALOAD) {
                        AbstractInsnNode in1 = in.getNext();
                        if(in1.getOpcode() == Opcodes.GETFIELD) {
                            FieldInsnNode in11 = (FieldInsnNode) in1;
                            AbstractInsnNode in2 = in1.getNext();
                            if(in2.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                                //insns.insert(in.getNext(), new InsnNode(0));
                                System.out.println("GETFIELD desc: " + in11.desc);
                                System.out.println("GETFIELD owner: " + in11.owner);
                                System.out.println("GETFIELD name: " + in11.name);
                                MethodInsnNode in22 = (MethodInsnNode) in2;
                                InsnList il = new InsnList();
                                /*
                                  il.add(new InsnNode(Opcodes.POP));
                                  il.add(new LdcInsnNode(new String("11")));
                                  il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "catg/CATG", "readString", "(Ljava/lang/String;)Ljava/lang/String;"));
                                */
                                il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "instrument/SymbolicMirror", "sgetText", "(Ljava/lang/Object;)Ljava/lang/String;"));
                                insns.remove(in22);
                                insns.insert(in11, il);
                            }
                        }
                    }
                }
            }

            ((List<MethodNode>) cn.methods).stream().forEach(f -> {
                    //                    f.instructions.toArray().stream().forEach(i -> System.out.println("goo"));
                });

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            cn.accept(cw);

            byte[] ret = cw.toByteArray();

            try {
                File file = new File("instrumented/sym-agent" + "/" + cname + ".class");
                File parent = new File(file.getParent());
                parent.mkdirs();
                FileOutputStream out = new FileOutputStream(file);
                out.write(ret);
                out.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cbuf;
    }

}
