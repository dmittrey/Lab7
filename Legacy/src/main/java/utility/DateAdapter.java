package utility;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date unmarshal(String aString) {
        try {
            return formatter.parse(aString);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public String marshal(Date aDate) {
        return formatter.format(aDate);
    }
}