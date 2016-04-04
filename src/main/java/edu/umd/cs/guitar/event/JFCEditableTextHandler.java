//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.event.JFCEventHandler;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.util.Debugger;
import edu.umd.cs.guitar.util.GUITARLog;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleEditableText;

public class JFCEditableTextHandler extends JFCEventHandler {
    private static String GUITAR_DEFAULT_TEXT = "GUITAR DEFAULT TEXT: !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~¡¢£¤¥¦§¨©ª«¬\u00ad®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿĀāĂăĄąĆćĈĉĊċČčĎďĐđĒēĔĕĖėĘęĚěĜĝĞğĠġĢģĤĥĦħĨĩĪīĬĭĮįİıĲĳĴĵĶķĸĹĺĻļĽľĿŀŁłŃńŅņŇňŉŊŋŌōŎŏŐőŒœŔŕŖŗŘřŚśŜŝŞşŠšŢţŤťŦŧŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽžſƀƁƂƃƄƅƆƇƈƉƊƋƌƍƎƏƐƑƒƓƔƕƖƗƘƙƚƛƜƝƞƟƠơƢƣƤƥƦƧƨƩƪƫƬƭƮƯưƱƲƳƴƵƶƷƸƹƺƻƼƽƾƿǀǁǂǃǍǎǏǐǑǒǓǔǕǖǗǘǙǚǛǜǝǞǟǠǡǢǣǤǥǦǧǨǩǪǫǬǭǮǯǰǾǿȀȁȂȃȄȅȆȇȈȉȊȋȌȍȎȏȐȑȒȓȔȕȖȗȘșȚțȜȝȞȟȠȡȢȣȤȥȦȧȨȩȪȫȬȭȮȯȰȱȲȳȴȵȶȷȸȹȺȻȼȽȾȿɀɁɂɃɄɅɆɇɈɉɊɋɌɍɎɏɐɑɒɓɔɕɖɗɘəɚɛɜɝɞɟɠɡɢɣɤɥɦɧɨɩɪɫɬɭɮɯɰɱɲɳɴɵɶɷɸɹɺɻɼɽɾɿʀʁʂʃʄʅʆʇʈʉʊʋʌʍʎʏʐʑʒʓʔʕʖʗʘʙʚʛʜʝʞʟʠʡʢʣʤʥʦʧʨʩʪʫʬʭʮʯʰʱʲʳʴʵʶʷʸʹ";

    public JFCEditableTextHandler() {
    }

    public void performImpl(GComponent gComponent, Hashtable<String, List<String>> optionalData) {
        ArrayList args = new ArrayList();
        args.add(GUITAR_DEFAULT_TEXT);
        this.performImpl(gComponent, args, optionalData);
    }

    protected void performImpl(GComponent gComponent, Object parameters, Hashtable<String, List<String>> optionalData) {
        if(gComponent != null) {
            if(parameters instanceof List) {
                List lParameter = (List)parameters;
                String sInputText;
                if(lParameter == null) {
                    sInputText = GUITAR_DEFAULT_TEXT;
                } else {
                    sInputText = lParameter.size() != 0?(String)lParameter.get(0):GUITAR_DEFAULT_TEXT;
                }

                Component component = this.getComponent(gComponent);
                AccessibleContext aContext = component.getAccessibleContext();
                AccessibleEditableText aTextEvent = aContext.getAccessibleEditableText();
                if(aTextEvent == null) {
                    System.err.println(this.getClass().getName() + " doesn\'t support");
                    return;
                }

                try {
                    aTextEvent.setTextContents(sInputText);
                } catch (Exception var16) {
                    try {
                        Method e1 = component.getClass().getMethod("setText", new Class[]{String.class});
                        e1.invoke(component, new Object[]{sInputText});
                        Debugger.pause(GUITAR_DEFAULT_TEXT);
                    } catch (SecurityException var11) {
                        GUITARLog.log.error(var11);
                    } catch (NoSuchMethodException var12) {
                        GUITARLog.log.error(var12);
                    } catch (IllegalArgumentException var13) {
                        GUITARLog.log.error(var13);
                    } catch (IllegalAccessException var14) {
                        GUITARLog.log.error(var14);
                    } catch (InvocationTargetException var15) {
                        GUITARLog.log.error(var15);
                    }
                }
            }

        }
    }

    public boolean isSupportedBy(GComponent gComponent) {
        if(!(gComponent instanceof JFCXComponent)) {
            return false;
        } else {
            JFCXComponent jComponent = (JFCXComponent)gComponent;
            Component component = jComponent.getComponent();
            AccessibleContext aContext = component.getAccessibleContext();
            if(aContext == null) {
                return false;
            } else {
                AccessibleEditableText event = aContext.getAccessibleEditableText();
                return event != null;
            }
        }
    }
}
