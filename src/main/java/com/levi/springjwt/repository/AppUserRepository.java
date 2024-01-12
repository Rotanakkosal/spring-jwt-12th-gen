package com.levi.springjwt.repository;

import com.levi.springjwt.model.dto.request.AppUserRequest;
import com.levi.springjwt.model.entity.AppUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppUserRepository {

    @Select("""
            INSERT INTO users (username, email, password)
            VALUES (#{user.username}, #{user.email}, #{user.password})
            RETURNING id
            """)
    @Result(property = "id", column = "id")
    Integer saveUser(@Param("user") AppUserRequest appUserRequest);

    @Insert("""
            INSERT INTO user_role
            (user_id, role_id)
            VALUES (#{userId}, #{roleId})
            """)
    void saveUserRole(Integer userId, Integer roleId);

    @Select("""
            SELECT * FROM users
            WHERE id = #{id}
            """)

    @Results(id = "userMap", value = {
            @Result(property = "roleName", column = "id",
                    many = @Many(select = "getRoleByUserId")
            ),
            @Result(property = "id", column = "id")
    })

    AppUser findUserById(Integer userId);

    @Select("""
            SELECT r.role_name FROM roles r
            INNER JOIN user_role ur
            ON r.id = ur.role_id
            WHERE user_id = #{userId}
            """)
//    @Result(property = "roleName", column = "role_name")
    String getRoleByUserId(Integer userId);

    @Select("""
            SELECT email FROM users
            WHERE email ILIKE #{email}
            """)
    String findEmail(String email);

    @Delete("""
            DELETE FROM users
            where id = #{userId}
            """)
    void deleteUserById(Integer userId);

    @Select("""
            SELECT * FROM users
            """)
    @ResultMap("userMap")
    List<AppUser> findAllUser();


    @Select("""
            SELECT * FROM users
            WHERE email = #{email}
            """)
    @ResultMap("userMap")
    AppUser findByEmail(String email);
}
