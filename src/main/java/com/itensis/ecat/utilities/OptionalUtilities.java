package com.itensis.ecat.utilities;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class OptionalUtilities {

    public static <T> T ifPresentElseThrow(Optional<T> optional){
        if (optional.isPresent()){
            return optional.get();
        }
        throw new EntityNotFoundException();
    }

}
