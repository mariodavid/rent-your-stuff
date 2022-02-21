package de.diedavids.jmix.rys.test_support.test_data;


public interface TestDataProvisioning<DTO, DTOBuilder, Entity> {

    DTOBuilder defaultData();

    Entity save(DTO dto);

    Entity saveDefault();

}