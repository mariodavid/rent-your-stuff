package de.diedavids.jmix.rys.entity;


public interface EntityRepository<DTO, Entity> {
    Entity save(DTO dto);
}