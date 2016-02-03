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

public class SymAgent implements ClassFileTransformer {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new SymAgent());
    }

    @Override
    public byte[] transform(ClassLoader loader, String cname, Class<?> cclass, ProtectionDomain d, byte[] cbuf) throws IllegalClassFormatException {
        if (!cname.startsWith("examples")) return cbuf;
        System.out.println("transforming: " + cname);

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
                    if (in.getOpcode() == Opcodes.ALOAD)
                        if(in.getNext().getOpcode() == Opcodes.GETFIELD)
                            if(in.getNext().getNext().getOpcode() == Opcodes.INVOKEVIRTUAL)
                                {
                                    //insns.insert(in.getNext(), new InsnNode(0));

                                    InsnList il = new InsnList();
                                    il.add(new InsnNode(Opcodes.POP));
                                    il.add(new LdcInsnNode(new String("11")));
                                    il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "catg/CATG", "readString", "(Ljava/lang/String;)Ljava/lang/String;"));
                                    insns.insert(in.getNext().getNext(), il);

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
