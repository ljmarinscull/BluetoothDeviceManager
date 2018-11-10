package com.bluetoothdevicemanager.data.model.mapper;

import java.util.List;

public abstract class Mapper <T1, T2>{

        /**
         * @param value Modedo a Transformar
         * @return Modelo transformado
         */
        public abstract T2 map(T1 value);


        /**
         * Mapeador que transforma en sentido contrario al map
         * @param value Modelo a transformar
         * @return Modelo transformado
         */
        public abstract T1 reverseMap(T2 value);

        /**
         * Mapeador que transforma en sentido contrario al listMap
         * @param value Modelo a transformar
         * @return Modelo transformado
         */
        public abstract List<T2> listMap(List<T1> value);

        /**
         * Mapeador que transforma en sentido contrario al listMap
         * @param value Modelo a transformar
         * @return Modelo transformado
         */
        public abstract List<T1> reverseListMap(List<T2>  value);
}
