package es.covent.mytrackingapp.data;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duanel Garrido on 19/10/2015.
 */
public class UserXMLParser {

    public static List<Usuario> parseFeed(String content) {

        try {

            boolean inDataItemTag = false;
            String currentTagName = "";
            Usuario us = null;
            List<Usuario> usList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("usuario")) {
                            inDataItemTag = true;
                            us = new Usuario(0,"","","","");
                            usList.add(us);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("usuario")) {
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        if (inDataItemTag && us != null) {
                            switch (currentTagName) {
                                case "usuarioId":
                                    us.setId(Integer.parseInt(parser.getText()));
                                    break;
                                case "usuarioNombre":
                                    us.setNombre(parser.getText());
                                default:
                                    break;
                            }
                        }
                        break;
                }
                eventType = parser.next();

            }
            return usList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
