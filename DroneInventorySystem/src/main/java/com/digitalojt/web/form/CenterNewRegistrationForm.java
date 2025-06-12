package com.digitalojt.web.form;

import com.digitalojt.web.consts.ModelAttributeContents;
import com.digitalojt.web.validation.CenterInfoNewRegistrationValidator;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotNull(message = "{requiredField.empty}")
    @NotBlank(message = "{requiredField.empty}")
	private String centerName;

	/**郵便番号*/
    @Size(max = 8, message = "{postCode.length.wrongInput}")
    @Pattern(regexp = "^[a-zA-Z0-9]{3}-[a-zA-Z0-9]{4}$", message = "{postCode.format.wrongInput}")
    @Pattern(regexp = "^[0-9-]+$", message = "{invalid.input}")
    @NotNull(message = "{requiredField.empty}")
    @NotBlank(message = "{requiredField.empty}")
	private String postCode;	
	
	/**住所*/
    @Size(max = 255, message = "{address.length.wrongInput}")
    @NotNull(message = "{requiredField.empty}")
    @NotBlank(message = "{requiredField.empty}")
	private String address;
	
	/**電話番号*/
    @Size(max = 20, message = "{telephoneNumber.length.wrongInput}")
    @NotNull(message = "{requiredField.empty}")
    @NotBlank(message = "{requiredField.empty}")
	private String telephoneNumber;
	
	/**管理者名*/
    @Size(max = 100, message = "{administratorName.length.wrongInput}")
    @NotNull(message = "{requiredField.empty}")
    @NotBlank(message = "{requiredField.empty}")
	private String administratorName;
	
	/**稼働ステータス*/
    @NotNull(message = "{requiredField.empty}") 
    @Max(value = 1, message = "{invalid.input}")
    @Min(value = 0, message = "{invalid.input}")
	private Integer operationalStatus = 0;
	
	/**最大容量(m3)*/
    @Size(max = 10, message = "{maximumCapacity.length.wrongInput}")
    @Pattern(regexp = "^[0-9]*$", message = "{invalid.input}")
	private String maximumCapacity;
	
	/**現在容量(m3)*/
    @Size(max = 10, message = "{currentCapacity.length.wrongInput}")
    @Pattern(regexp = "^[0-9]*$", message = "{invalid.input}")
	private String currentCapacity;
	
	/**備考*/
    @Size(max = 255, message = "{remarks.length.wrongInput}")
	private String remarks;
	
}
