package com.example.dao;

import java.util.List;

import com.example.model.CourseModel;
import org.apache.ibatis.annotations.*;

import com.example.model.StudentModel;

@Mapper
public interface StudentMapper
{
    @Select("select course.id_course, name, credits " +
            "from studentcourse join course " +
            "on studentcourse.id_course = course.id_course " +
            "where studentcourse.npm = #{npm}")
    List<CourseModel> Courses (@Param("npm") String npm);
    @Select("select npm, name, gpa from student where npm = #{npm}")
    @Results(value = {
            @Result(property="npm", column="npm"),
            @Result(property="name", column="name"),
            @Result(property="gpa", column="gpa"),
            @Result(property="courses", column="npm",
                    javaType = List.class,
                    many=@Many(select="Courses"))
    })
    StudentModel selectStudent (@Param("npm") String npm);

    @Select("select npm, name, gpa from student")
    @Results(value = {
            @Result(property="npm", column="npm"),
            @Result(property="name", column="name"),
            @Result(property="gpa", column="gpa"),
            @Result(property="courses", column="npm",
                    javaType = List.class,
                    many=@Many(select="Courses"))
    })
    List<StudentModel> selectAllStudents ();

    @Insert("INSERT INTO student (npm, name, gpa) VALUES (#{npm}, #{name}, #{gpa})")
    void addStudent (StudentModel student);

    @Delete("DELETE FROM student WHERE npm = #{npm}")
    void deleteStudent (@Param("npm") String npm);

    @Update("UPDATE student SET name = #{name}, gpa = #{gpa} WHERE npm = #{npm}")
    void updateStudent (StudentModel student);



    @Select("select student.npm, student.name " +
            "from studentcourse join student " +
            "on studentcourse.npm = student.npm " +
            "where studentcourse.id_course = #{id}")
    List<StudentModel> studentSelect (@Param("id") String id);

    @Select("select id_course, name, credits from course where id_course = #{id}")
    @Results(value = {
            @Result(property="idCourse", column="id_course"),
            @Result(property="name", column="name"),
            @Result(property="credits", column="credits"),
            @Result(property="students", column="id_course",
                    javaType = List.class,
                    many=@Many(select="studentSelect"))
    })
    CourseModel selectCourses (@Param("id") String id);
}
