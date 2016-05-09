package it.ldsoftware.primavera.vaadin.util;

import it.ldsoftware.primavera.dto.base.BaseDTO;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luca on 03/05/16.
 * This class manages data that can be downloaded
 */
public class DownloadSession {
    private static Map<String, List<? extends BaseDTO<?>>> downloadQueue;

    /**
     * Inserts a list to the queue and returns the download ID to be used to redirect to the download controller
     *
     * @param list the list of objects
     * @return the ID of the session to retrieve the item just inserted
     */
    public static String addToQueue(List<? extends BaseDTO<?>> list) {
        if (downloadQueue == null)
            initQueue();

        String downloadID = generateDownloadID();
        downloadQueue.put(downloadID, list);
        return downloadID;
    }

    /**
     * This function generates a random string to uniquely identify the download session
     *
     * @return the random ID of the download session
     */
    private static String generateDownloadID() {
        String tmpId;
        do {
            tmpId = RandomStringUtils.random(50, true, true);
        } while (downloadQueue.containsKey(tmpId));

        return tmpId;
    }

    public static List<? extends BaseDTO<?>> getDtoList(String downloadID) {
        return downloadQueue.remove(downloadID);
    }

    private static void initQueue() {
        downloadQueue = new HashMap<>();
    }
}
