package plugin.wuguangliang.dimens.utils;
 
import java.awt.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;
 
public class BrowserUtil {

    public static void openByDefault(String url) throws Exception {
        Desktop desktop = Desktop.getDesktop();
        if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
            URI uri = new URI(url);
            desktop.browse(uri);
        }
    }
}