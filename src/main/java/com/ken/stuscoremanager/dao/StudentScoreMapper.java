package com.ken.stuscoremanager.dao;

import com.ken.stuscoremanager.entity.StudentScore;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/13 8:05
 * @description 学生成绩
 */
@Repository
public interface StudentScoreMapper {

    /**
     * 根据学号查询成绩
     * @param name
     * @return
     *
     * 使用xml方式的多表联合查询，多对多的方式，这里有三张表，一个主表两个从表
     * <select id="getScoreByStuName" resultType="Score">
     *         select ss.stuno, s.name, c.coursename, ss.score, ss.type from stu_sco ss join stu s
     *         on ss.stuno = s.stuno
     *         join course c on ss.courseid = c.courseid
     *         where s.name=#{name}
     * </select>
     */
//    @Select("select * from stu_sco")
//    @Results({
//            @Result(property = "stuno", column = "stuno"),
//            @Result(property = "courseid", column = "courseid"),
//            @Result(property = "stuno", column = "stuno", javaType = List.class,
//                many = @Many(select = "com.ken.stuscoremanager.dao.StudentMapper.findByStuno")
//            ),
//            @Result(property = "courseid", column = "courseid", javaType = List.class,
//                    many = @Many(select = "com.ken.stuscoremanager.dao.CourseMapper.findCourseId")
//            )
//    })
    @Select("select ss.stuno, s.name, c.coursename, ss.score, ss.type from stu_sco ss join stu s on ss.stuno = s.stuno join course c on ss.courseid = c.courseid where s.name=#{name}")
    List<StudentScore> getScoreByName(String name);


    /**
     * 这里中的数字分别对应参数列表中参数的顺序
     * @param offset
     * @param limit
     * @return
     */
//    @Select("select ss.stuno, s.name, c.coursename, ss.score, ss.type from stu_sco ss join stu s\n" +
//            "        on ss.stuno = s.stuno\n" +
//            "        join course c on ss.courseid = c.courseid\n" +
//            "        where c.coursename=#{2} and ss.type=#{3} and s.stuno like CONCAT('%', #{0},#{1},'%')\n" +
//            "        limit #{4},#{5}")

    @Select("select ss.stuno, s.name, c.coursename, ss.score, ss.type from stu_sco ss join stu s\n" +
            "        on ss.stuno = s.stuno\n" +
            "        join course c on ss.courseid = c.courseid\n" +
            "        where c.coursename=#{coursename} and ss.type=#{type} and s.stuno like CONCAT('%', #{grade},#{cla},'%')\n" +
            "        limit #{offset},#{limit}")
    List<StudentScore> findScore(@Param("grade") String grade,@Param("cla") String cla,@Param("coursename") String coursename,@Param("type") String type,@Param("offset") Integer offset,@Param("limit") Integer limit);


    /**
     * 分数统计
     * @param coursename
     * @param grade
     * @param cla
     * @param type
     * @return
     */
    @Select("select COUNT(*) from stu_sco ss join stu s\n" +
            "        on ss.stuno = s.stuno\n" +
            "        join course c on ss.courseid = c.courseid\n" +
            "        where c.coursename=#{2} and ss.type=#{3} and s.stuno like CONCAT('%', #{0},#{1},'%')")
    List<StudentScore> scoreCount(@Param("0") String grade,@Param("1") String cla,@Param("2") String courseName,@Param("3") String type);


    /**
     * 成绩统计
     * @param coursename
     * @param grade
     * @param cla
     * @param type
     * @return 四张表联合查询的结果
     */
    @Select("select ss.stuno, s.name, c.coursename, ss.score, ss.type from stu_sco ss join stu s\n" +
            "        on ss.stuno = s.stuno\n" +
            "        join course c on ss.courseid = c.courseid\n" +
            "        join teacher t on t.course=ss.courseid\n" +
            "        where c.coursename=#{0} and ss.type=#{3} and t.gra_class like CONCAT('%', #{1},#{2},'%')")
    List<StudentScore> findGcsCount(@Param("0") String coursename, @Param("1") String grade,@Param("2") String cla,@Param("3") String type);

    /**
     * 对比统计
     * @param coursename
     * @param grade
     * @return
     * 先关联再查询
     */
    @Select("select ss.stuno, s.name, c.coursename, ss.score, ss.type from stu_sco ss \n" +
            "\t\t\t\tjoin stu s on ss.stuno=s.stuno\n" +
            "        join course c on c.courseid=ss.courseid\n" +
            "        where c.coursename=#{0} and ss.type='已批改' and s.stuno like concat('%', #{1}, '%')")
    List<StudentScore> getGscomp(@Param("0") String coursename,@Param("1") String grade);
}
