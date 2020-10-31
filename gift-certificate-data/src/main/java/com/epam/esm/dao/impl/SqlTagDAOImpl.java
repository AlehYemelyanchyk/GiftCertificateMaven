package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SqlTagDAOImpl implements TagDAO {

    private JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_TAGS_SQL_QUERY = "SELECT * FROM tags";
    private static final String FIND_TAGS_BY_ID_SQL_QUERY = "SELECT * FROM tags WHERE id = ?";
    private static final String FIND_TAGS_BY_NAME_SQL_QUERY = "SELECT * FROM tags WHERE name = ?";
    private static final String SAVE_TAGS_SQL_QUERY = "INSERT INTO tags (name) VALUES (?)";
    private static final String DELETE_TAGS_BY_NAME_SQL_QUERY = "DELETE FROM tags WHERE name = ?";
    private static final String DELETE_TAGS_BY_ID_SQL_QUERY = "DELETE FROM tags WHERE id = ?";

    public SqlTagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(
                FIND_ALL_TAGS_SQL_QUERY,
                new BeanPropertyRowMapper<>(Tag.class)
        );
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        return jdbcTemplate.query(
                FIND_TAGS_BY_ID_SQL_QUERY,
                new Object[]{id},
                new BeanPropertyRowMapper<>(Tag.class)
        ).stream().findFirst();
    }

    @Override
    public Optional<Tag> findByName(String name){
        return jdbcTemplate.query(
                FIND_TAGS_BY_NAME_SQL_QUERY,
                new Object[]{name},
                new BeanPropertyRowMapper<>(Tag.class)
        ).stream().findFirst();
    }

    @Override
    public Optional<Tag> save(Tag object){
        jdbcTemplate.update(
                SAVE_TAGS_SQL_QUERY,
                object.getName()
        );
        return findByName(object.getName());
    }

    @Override
    public Optional<Tag> update(Tag object){
        return Optional.empty();
    }

    @Override
    public void delete(Tag object) {
        jdbcTemplate.update(
                    DELETE_TAGS_BY_NAME_SQL_QUERY,
                    object.getId()
        );
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update(
                DELETE_TAGS_BY_ID_SQL_QUERY,
                id
        );
    }
}
