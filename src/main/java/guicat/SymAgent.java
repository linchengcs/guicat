package guicat;


import org.apache.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Iterator;
import java.util.List;

public class SymAgent implements ClassFileTransformer {

    private Logger logger = Logger.getLogger(SymbolicMirror.class);


    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new SymAgent());
        String value = System.getProperty("conf");
        //System.out.println(value);
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

                /*
                GETFIELD   in1b
                INVOKEVIRTUAL  in2b
                 */
                /*
                : aload_0
       9: getfield      #2                  // Field val$nameInput:Ljavax/swing/JTextField;
      12: invokevirtual #7                  // Method javax/swing/JTextField.getAccessibleContext:()Ljavax/accessibility/AccessibleContext;
      15: invokevirtual #8                  // Method javax/accessibility/AccessibleContext.getAccessibleName:()Ljava/lang/String;
      18: ldc           #9                  // String nameInputAccessibleNameTEST
      20: invokevirtual #10                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
      23: ifeq          34
      26: aload_0
      27: getfield      #2                  // Field val$nameInput:Ljavax/swing/JTextField;
      30: invokevirtual #6                  // Method javax/swing/JTextField.getText:()Ljava/lang/String;
      33: pop

                 */
                while (j.hasNext()) {
                    AbstractInsnNode whyShouldIExist = j.next();
                    AbstractInsnNode in1a = whyShouldIExist.getNext();
                    if(in1a==null)break;
                    if (in1a.getOpcode() == Opcodes.GETFIELD) {
                        FieldInsnNode in1b = (FieldInsnNode) in1a;
                        if (in1b.desc.startsWith("Ljavax/swing/JTextField")) {
                            //todo, check accessibleName
                            AbstractInsnNode in2a = in1a.getNext();
                            if (in2a == null)
                                break;
                            if (in2a.getOpcode() == Opcodes.INVOKEVIRTUAL) {

                                MethodInsnNode in2b = (MethodInsnNode) in2a;
                                /*
                                System.out.println("GETFIELD desc: " + in1b.desc);
                                System.out.println("GETFIELD owner: " + in1b.owner);
                                System.out.println("GETFIELD name: " + in1b.name);
                                System.out.println("INVOKEVIRTUAL desc: " + in2b.desc);
                                System.out.println("INVOKEVIRTUAL owner: " + in2b.owner);
                                System.out.println("INVOKEVIRTUAL name: " + in2b.name);*/
                                if (in2b.name.equals("getText")) {
                                    InsnList il = new InsnList();
                                    il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "guicat/SymbolicMirror", "sgetText", "(Ljava/lang/Object;)Ljava/lang/String;"));
                                    insns.remove(in2b);
                                    insns.insert(in1b, il);
                                }
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
