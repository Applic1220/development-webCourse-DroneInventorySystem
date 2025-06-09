package com.digitalojt.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digitalojt.web.entity.CenterInfo;

/**
 * センター情報テーブルリポジトリー
 *
 * @author dotlife
 * 
 */
public interface CenterInfoRepository extends JpaRepository<CenterInfo, Integer> {

	/**
	 * 引数に合致する在庫センター情報を取得
	 * 
	 * @param centerName
	 * @param region
	 * @param storageCapacityFrom
	 * @param storageCapacityTo
	 * @return paramで検索した結果
	 */
	@Query("SELECT s, " +
			"CASE " +
			" WHEN s.address LIKE '愛知県%' THEN 1 " +
			" WHEN s.address LIKE '青森県%' THEN 2 " +
			" WHEN s.address LIKE '秋田県%' THEN 3 " +
			" WHEN s.address LIKE '石川県%' THEN 4 " +
			" WHEN s.address LIKE '茨城県%' THEN 5 " +
			" WHEN s.address LIKE '岩手県%' THEN 6 " +
			" WHEN s.address LIKE '愛媛県%' THEN 7 " +
			" WHEN s.address LIKE '大分県%' THEN 8 " +
			" WHEN s.address LIKE '大阪府%' THEN 9 " +
			" WHEN s.address LIKE '岡山県%' THEN 10 " +
			" WHEN s.address LIKE '沖縄県%' THEN 11 " +
			" WHEN s.address LIKE '香川県%' THEN 12 " +
			" WHEN s.address LIKE '鹿児島県%' THEN 13 " +
			" WHEN s.address LIKE '神奈川県%' THEN 14 " +
			" WHEN s.address LIKE '岐阜県%' THEN 15 " +
			" WHEN s.address LIKE '京都府%' THEN 16 " +
			" WHEN s.address LIKE '熊本県%' THEN 17 " +
			" WHEN s.address LIKE '群馬県%' THEN 18 " +
			" WHEN s.address LIKE '高知県%' THEN 19 " +
			" WHEN s.address LIKE '埼玉県%' THEN 20 " +
			" WHEN s.address LIKE '佐賀県%' THEN 21 " +
			" WHEN s.address LIKE '滋賀県%' THEN 22 " +
			" WHEN s.address LIKE '静岡県%' THEN 23 " +
			" WHEN s.address LIKE '島根県%' THEN 24 " +
			" WHEN s.address LIKE '千葉県%' THEN 25 " +
			" WHEN s.address LIKE '東京都%' THEN 26 " +
			" WHEN s.address LIKE '徳島県%' THEN 27 " +
			" WHEN s.address LIKE '栃木県%' THEN 28 " +
			" WHEN s.address LIKE '鳥取県%' THEN 29 " +
			" WHEN s.address LIKE '富山県%' THEN 30 " +
			" WHEN s.address LIKE '長崎県%' THEN 31 " +
			" WHEN s.address LIKE '長野県%' THEN 32 " +
			" WHEN s.address LIKE '奈良県%' THEN 33 " +
			" WHEN s.address LIKE '新潟県%' THEN 34 " +
			" WHEN s.address LIKE '兵庫県%' THEN 35 " +
			" WHEN s.address LIKE '広島県%' THEN 36 " +
			" WHEN s.address LIKE '福井県%' THEN 37 " +
			" WHEN s.address LIKE '福岡県%' THEN 38 " +
			" WHEN s.address LIKE '福島県%' THEN 39 " +
			" WHEN s.address LIKE '北海道%' THEN 40 " +
			" WHEN s.address LIKE '三重県%' THEN 41 " +
			" WHEN s.address LIKE '宮城県%' THEN 42 " +
			" WHEN s.address LIKE '宮崎県%' THEN 43 " +
			" WHEN s.address LIKE '山形県%' THEN 44 " +
			" WHEN s.address LIKE '山口県%' THEN 45 " +
			" WHEN s.address LIKE '山梨県%' THEN 46 " +
			" WHEN s.address LIKE '和歌山県%' THEN 47 " +
			"  ELSE 999 END AS address_suffix " +
			"FROM CenterInfo s WHERE  " +
			"(:centerName = '' OR s.centerName LIKE %:centerName%) AND " +
			"(:region = '' OR s.address LIKE %:region%) AND " +
			"(:storageCapacityFrom IS NULL OR s.currentStorageCapacity >= :storageCapacityFrom) AND " +
			"(:storageCapacityTo IS NULL OR s.currentStorageCapacity <= :storageCapacityTo) AND " +
			"(s.deleteFlag = 0) AND " +
			"(s.operationalStatus = 0) " +
			"ORDER BY address_suffix,s.address ")
	List<CenterInfo> findActiveCenters(
			String centerName,
			String region,
			Integer storageCapacityFrom,
			Integer storageCapacityTo);
}
