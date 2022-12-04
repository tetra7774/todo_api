package com.example.demo.repository.task;



import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TaskRepository {

    @Select("SELECT id, title FROM tasks WHERE id = #{taskID}")
    Optional<TaskRecord> select(Long taskID);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO tasks (title) VALUES (#{title})")
    void insert(TaskRecord record);

    @Select("SELECT id, title FROM tasks LIMIT #{limit} OFFSET #{offset}")
    List<TaskRecord> selectList(@Param("limit") int limit, @Param("offset") long offset);

    @Update("UPDATE tasks SET title = #{title} WHERE id = #{id}")
    void update(TaskRecord taskRecord);

    @Delete("DELETE FROM tasks WHERE id = #{taskID}")
    void delete(Long taskID);
}
