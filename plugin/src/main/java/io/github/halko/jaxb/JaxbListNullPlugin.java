package io.github.halko.jaxb;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import static java.lang.invoke.MethodHandles.Lookup.PUBLIC;
import java.util.Map.Entry;
import org.xml.sax.ErrorHandler;

public class JaxbListNullPlugin extends Plugin {

    @Override
    public String getOptionName() {
        return "Xjaxb-listnull-check";
    }

    @Override
    public int parseArgument(Options opt, String[] args, int i) {
        return 1;
    }

    @Override
    public String getUsage() {
        return "  -Xjaxb-listnull-check    :  xjc listnull-check plugin";
    }

    @Override
    public boolean run(Outline model, Options opt, ErrorHandler errorHandler) {
        for (ClassOutline o : model.getClasses()) {

            for (Entry<String, JFieldVar> e : o.implClass.fields().entrySet()) {
                if (e.getValue().type().fullName().startsWith("java.util.List")) {
                    JMethod method = o.implClass.method(PUBLIC, boolean.class, "is" + firstToUpper(e.getValue().name()) + "Null");
                    JBlock body = method.body();
                    JFieldVar v = e.getValue();
                    body._return(v.eq(JExpr._null()));
                }
            }

        }

        return true;
    }

    private String firstToUpper(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(str.charAt(0)));
        sb.append(str.substring(1));
        return sb.toString();
    }
}
