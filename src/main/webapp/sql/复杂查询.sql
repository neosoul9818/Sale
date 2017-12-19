SELECT sku.id sku_id,spu.id spu_id, tm.id tm_id,spu.*, sku.*, tm.*  FROM t_mall_product spu,t_mall_sku sku,t_mall_tm_class tc,t_mall_trade_mark tm
		WHERE spu.id=sku.shp_id AND  spu.flbh1 = tc.flbh1 AND spu.pp_id = tm.id
		AND tc.pp_id = tm.id
		AND spu.flbh2 = #{class_2_id}
		${sql}
		
		
		
		
		
		
		
SELECT
	sku.id sku_id,
	spu.id spu_id,
	image.id image_id,
	sku.*, spu.*, image.*, attr.shxm_mch,
	CONCAT(val.shxzh, val.shxzh_mch)
FROM
	t_mall_sku sku,
	t_mall_product spu,
	t_mall_product_image image,
	t_mall_sku_attr_value av,
	t_mall_attr attr,
	t_mall_value val
WHERE
	spu.id = sku.shp_id
AND spu.id = image.shp_id
AND av.sku_id = sku.id
AND av.shxm_id = attr.id
AND av.shxzh_id = val.id
AND spu.id = 6
AND sku.id = 3