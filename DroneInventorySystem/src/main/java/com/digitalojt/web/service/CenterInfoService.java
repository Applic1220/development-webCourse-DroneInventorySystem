package com.digitalojt.web.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalojt.web.consts.ModelAttributeContents;
import com.digitalojt.web.entity.CenterInfo;
import com.digitalojt.web.form.CenterNewRegistrationForm;
import com.digitalojt.web.repository.CenterInfoRepository;

import lombok.RequiredArgsConstructor;
/**
 * 在庫センター情報画面のサービスクラス
 *
 * @author dotlife
 * 
 */
@Service
@RequiredArgsConstructor
public class CenterInfoService {

	/** センター情報テーブル リポジトリー */
	private final CenterInfoRepository repository;

	/**
	 * 在庫センター情報を全建検索で取得
	 * 
	 * @return
	 */
	public List<CenterInfo> getCenterInfoData() {
		return repository.findAll();
	}

	/**
	 * 引数に合致する在庫センター情報を取得
	 * 
	 * @param centerName
	 * @param region 
	 * @param storageCapacityFrom
	 * @param storageCapacityTo 
	 * @return
	 */
	public List<CenterInfo> getCenterInfoData(String centerName, String region, Integer storageCapacityFrom, Integer storageCapacityTo) {
		return repository.findActiveCenters(centerName, region, storageCapacityFrom, storageCapacityTo);
	}
	
	
	
    private final CenterInfoRepository centerInfoRepository;

    /**
     * 在庫センター情報登録
     *
     * @param form 登録する在庫センターのフォームデータ
     * @return 登録されたCenterInfoエンティティ
     */
    @Transactional 
    public CenterInfo registerNewCenter(CenterNewRegistrationForm form) {
        // フォームデータからEntityへの変換
        CenterInfo centerInfo = new CenterInfo();
        centerInfo.setCenterName(form.getCenterName());
        centerInfo.setPostCode(form.getPostCode());
        centerInfo.setAddress(form.getAddress());
        centerInfo.setPhoneNumber(form.getTelephoneNumber()); 
        centerInfo.setManagerName(form.getAdministratorName()); 
        centerInfo.setOperationalStatus(form.getOperationalStatus());
        centerInfo.setMaxStorageCapacity(String.valueOf(form.getMaximumCapacity()));
        centerInfo.setCurrentStorageCapacity(String.valueOf(form.getCurrentCapacity()));
        centerInfo.setNotes(form.getRemarks()); 
        centerInfo.setDeleteFlag(ModelAttributeContents.DELETE_FLG_FALSE); 
        centerInfo.setCreateDate(LocalDateTime.now());
        centerInfo.setUpdateDate(LocalDateTime.now());

        return centerInfoRepository.save(centerInfo);
    }
	

	

}
