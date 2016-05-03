package it.ldsoftware.commons.vaadin.controllers;

import it.ldsoftware.commons.dto.base.BaseDTO;
import it.ldsoftware.commons.i18n.LocalizationService;
import it.ldsoftware.commons.vaadin.util.DownloadSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

/**
 * Created by luca on 03/05/16.
 * This controller is used to export grid data into csv files.
 * <p>
 * To correctly use this controller first put the list of DTO that you want to
 * download into a @{link DownloadSession}, and then point the browser
 * to the session id that comes out of the put operation.
 */
@Controller
public class ExportController {
    public static final String ADDRESS_EXPORT = "/exportCSV/";

    @Autowired
    MessageSource messageSource;

    private LocalizationService svc;

    @ResponseBody
    @RequestMapping(path = ADDRESS_EXPORT + "{sessionID}", produces = "text/csv")
    public String downloadCSV(@PathVariable String sessionID, Locale l, HttpServletResponse resp) {
        StringBuilder sb = new StringBuilder("");
        svc = new LocalizationService(messageSource, l);

        List<? extends BaseDTO<?>> list = DownloadSession.getDtoList(sessionID);
        BaseDTO<?> firstObject = list.get(0);
        if (!list.isEmpty()) {
            sb.append(generateCSVCaptions(firstObject, l));
            list.forEach(o -> sb.append(generateCSVLine(o)));
        }

        resp.setHeader("Content-Disposition",
                "attachment; filename=" + generateFileName(firstObject));

        return sb.toString();
    }

    private String generateCSVCaptions(BaseDTO<?> firstObject, Locale l) {
        StringBuilder sb = new StringBuilder();
//        Iterator<String> iter = Arrays.stream(firstObject.getCSVFields()).iterator();
//        while (iter.hasNext()) {
//            sb.append(svc.translate("field." + iter.next()));
//            if (iter.hasNext())
//                sb.append(";");
//        }
        return sb.toString().concat("\n");
    }

    private String generateCSVLine(BaseDTO<?> object) {
        StringBuilder sb = new StringBuilder();
//        Iterator<String> iter = Arrays.stream(object.getCSVFields()).iterator();
//        while (iter.hasNext()) {
//            Method m = null;
//            try {
//                m = object.getClass().getMethod("get" + StringUtils.capitalize(iter.next()));
//            } catch (NoSuchMethodException ignored) {
//
//            }
//            if (m == null) {
//                try {
//                    m = object.getClass().getMethod("is" + StringUtils.capitalize(iter.next()));
//                } catch (NoSuchMethodException ignored) {
//
//                }
//            }
//
//            if (m != null) {
//                try {
//                    sb.append(m.invoke(object));
//                } catch (Exception e) {
//                    sb.append("");
//                }
//            }
//
//            if (iter.hasNext())
//                sb.append(";");
//        }
        return sb.toString().concat("\n");
    }

    private String generateFileName(BaseDTO<?> object) {
        StringBuilder sb = new StringBuilder("Export_");
        String className = object.getClass().getSimpleName();
        if (className.endsWith("DTO"))
            className = className.substring(0, className.lastIndexOf("DTO"));

        sb.append(className);
        sb.append(".csv");

        return sb.toString();
    }
}
