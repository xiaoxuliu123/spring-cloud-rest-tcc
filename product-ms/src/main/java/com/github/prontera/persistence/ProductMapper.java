package com.github.prontera.persistence;

import com.github.prontera.MyBatisRepository;
import com.github.prontera.domain.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface ProductMapper extends CrudMapper<Product> {
    List<Product> selectAll(@Param("offset") int offset, @Param("limited") int limited);

    /**
     *  <update id="consumeStock">
          UPDATE t_product
         SET stock = stock - 1
         WHERE id = #{productId} AND stock > 0
        </update>
     */
    int consumeStock(@Param("productId") Long productId);

    /**
     *   <update id="returnReservedStock">
             UPDATE t_product
             SET stock = stock + 1
             WHERE id = #{productId}
         </update>
     *
     */
    int returnReservedStock(@Param("productId") Long productId);
}