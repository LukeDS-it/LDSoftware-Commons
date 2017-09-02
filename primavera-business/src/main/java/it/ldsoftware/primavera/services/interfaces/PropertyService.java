package it.ldsoftware.primavera.services.interfaces;

import it.ldsoftware.primavera.presentation.base.AppPropertyDTO;

/**
 * @author Luca Di Stefano
 */
public interface PropertyService extends BusinessService<AppPropertyDTO> {

    AppPropertyDTO findByKey(String key);

}
