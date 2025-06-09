package com.digitalojt.web.form;

import com.digitalojt.web.consts.ModelAttributeContents;
import com.digitalojt.web.validation.CenterInfoNewRegistrationValidator;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 在庫センター情報画面のフォームクラス
 * 
 * @author dotlife
 *
 */
@Data
@CenterInfoNewRegistrationValidator
@NoArgsConstructor 
public class CenterNewRegistrationForm {

	/**センター名*/
    @Size(max = ModelAttributeContents.MAX_CENTER_NAME_LENGTH, message = "{centerName.length.wrongInput}")
	private String centerName;

	/**郵便番号*/
	private String postCode;	
	
	/**住所*/
	private String address;
	
	/**電話番号*/
	private String telephoneNumber;
	
	/**管理者名*/
	private String administratorName;
	
	/**稼働ステータス*/
	private Integer operationalStatus = 1;
	
	/**最大容量(m3)*/
	private String maximumCapacity;
	
	/**現在容量(m3)*/
	private String currentCapacity;
	
	/**備考*/
	private String remarks;
	
}
