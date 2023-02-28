package com.example.core.service;

import java.util.List;

/**
 * todo: complete documentation
 *
 * @param <RD> - Response DTO - data transfer object, that will be returned to user
 * @param <SD> - Save DTO - data transfer object, that our server will get from client to save it in DB
 * @param <I>  - Unique identifier of a DTO, which will be saved in DB
 */
public interface FootballService<RD, SD, I> {

    List<RD> findAll();

    RD findById(I id);

    RD save(SD dto);

    RD update(I id, SD dto);

    RD delete(I id);
}
