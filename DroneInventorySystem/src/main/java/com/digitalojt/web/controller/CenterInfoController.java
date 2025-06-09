package com.digitalojt.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.digitalojt.web.consts.ErrorMessage;
import com.digitalojt.web.consts.InvalidCharacter;
import com.digitalojt.web.consts.LogMessage;
import com.digitalojt.web.consts.ModelAttributeContents;
import com.digitalojt.web.consts.Region;
import com.digitalojt.web.consts.UrlConsts;
import com.digitalojt.web.entity.CenterInfo;
import com.digitalojt.web.exception.ErrorMessageHelper;
import com.digitalojt.web.form.CenterInfoForm;
import com.digitalojt.web.form.CenterNewRegistrationForm;
import com.digitalojt.web.service.CenterInfoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
/**
 * 在庫センター情報画面のコントローラークラス
 * 
 * @author dotlife
 *
 */
@Controller
@RequiredArgsConstructor
public class CenterInfoController extends AbstractController {

	/** センター情報 サービス */
	private final CenterInfoService centerInfoService;

	/**
	 * 都道府県Enumをリストに変換
	 * 
	 * @return
	 */
	@ModelAttribute(ModelAttributeContents.REGIONS)
	public List<Region> populateRegions() {
		return Arrays.asList(Region.values());
	}

	/**
	 * 初期表示
	 * 
	 * @param model
	 * @retur
	 */
	@GetMapping(UrlConsts.CENTER_INFO)
	public String index(Model model) {
		logStart(LogMessage.HTTP_GET);

		// 在庫センター情報画面に表示するデータを取得
		List<CenterInfo> centerInfoList = centerInfoService.getCenterInfoData();

		// 画面表示用に商品情報リストをセット
		model.addAttribute(ModelAttributeContents.CENTER_INFO_LIST, centerInfoList);

		logEnd(LogMessage.HTTP_GET);

		return UrlConsts.CENTER_INFO_INDEX;
	}

	/**
	 * 検索結果表示
	 * 
	 * @param model
	 * @param form
	 * @return
	 */
	@GetMapping(UrlConsts.CENTER_INFO_SEARCH)
	public String search(Model model, @Valid CenterInfoForm form, BindingResult bindingResult) {
		logStart(LogMessage.HTTP_GET);

		// 入力値のバリデーションチェック
		if (bindingResult.hasErrors()) {
			handleValidationError(model, bindingResult, form);
			return UrlConsts.CENTER_INFO_INDEX;
		}

		// 検索条件に基づいて在庫センター情報を取得
		List<CenterInfo> centerInfoList = centerInfoService.getCenterInfoData(form.getCenterName(), form.getRegion(),form.getStorageCapacityFrom() ,form.getStorageCapacityTo() );

		// 画面表示用に商品情報リストをセット
		model.addAttribute(ModelAttributeContents.CENTER_INFO_LIST, centerInfoList);

		logEnd(LogMessage.HTTP_GET);

		return UrlConsts.CENTER_INFO_INDEX;
	}
	/**
	 * 新規登録初期表示
	 * 
	 * @param model
	 * @retur
	 */
	@GetMapping(UrlConsts.CENTER_INFO_NEW_REGISTRATION_INFO)
	public String newRegistrationInfo(Model model) {
		logStart(LogMessage.HTTP_GET);
		
		model.addAttribute("centerNewRegistrationForm", new CenterNewRegistrationForm()); 

		logEnd(LogMessage.HTTP_GET);

		return UrlConsts.CENTER_INFO_NEW_REGISTRATION_INFO;
	}	
	/**
	 * 新規登録実行
	 * 
	 * @param model
	 * @param form
	 * @return
	 */
	@PostMapping(UrlConsts.CENTER_INFO_NEW_REGISTRATION_EXEC)
	public String newRegistrationExecution(Model model, @Valid CenterNewRegistrationForm form, BindingResult bindingResult) {
		logStart(LogMessage.HTTP_POST);
		// 入力値のバリデーションチェック
		if (bindingResult.hasErrors()) {
			handleValidationErrorForRegistration(model, bindingResult, form);
			model.addAttribute("centerNewRegistrationForm", form); 
			return UrlConsts.CENTER_INFO_NEW_REGISTRATION_INFO;
		}
		
		// バリデーション成功後の処理
		// Service層のトランザクションメソッドを呼び出してデータベースに保存
		try {
		    centerInfoService.registerNewCenter(form);

		    logEnd(LogMessage.HTTP_POST);
		    // 一覧ページへリダイレクト
		     return "redirect:" + UrlConsts.CENTER_INFO; 
		     
		} catch (Exception e) {
		    // データベース保存時のエラーハンドリング
		    bindingResult.reject("unexpected.error",ErrorMessage.UNEXPECTED_ERROR_MESSAGE); 
		    model.addAttribute("centerNewRegistrationForm", form); 
		    return UrlConsts.CENTER_INFO_NEW_REGISTRATION_INFO; 
		}

	}
	/**
	 * バリデーションエラー処理(検索)
	 * 
	 * @param model
	 * @param bindingResult
	 * @param form
	 */
	private void handleValidationError(Model model, BindingResult bindingResult, CenterInfoForm form) {
		// エラーメッセージをリストに格納
		StringBuilder errorMsg = new StringBuilder();

		// フィールドごとのエラーメッセージを取得し、リストに追加
		bindingResult.getGlobalErrors().forEach(error -> {
			String message = error.getDefaultMessage();
			errorMsg.append(message).append("\r\n"); // メッセージを改行で区切って追加
		});

		// エラーメッセージをモデルに追加
		model.addAttribute(LogMessage.FLASH_ATTRIBUTE_ERROR, errorMsg.toString());

		logValidationError(LogMessage.HTTP_POST, form + " " + errorMsg.toString());
	}
	
	/**
	 *　登録処理バリデーションエラー処理
	 * 
	 * @param model
	 * @param bindingResult
	 * @param form
	 */
	private void handleValidationErrorForRegistration(Model model, BindingResult bindingResult, CenterNewRegistrationForm form) {
		// エラーメッセージをリストに格納
		StringBuilder errorMsg = new StringBuilder();

		// フィールドごとのエラーメッセージを取得し、リストに追加
		bindingResult.getGlobalErrors().forEach(error -> {
			String message = error.getDefaultMessage();
			errorMsg.append(message).append("\r\n"); // メッセージを改行で区切って追加
		});
		
		// 対象コンポーネントにエラー情報を紐づけ
	    // センター名空白のバリデーション
	    if (isNullOrEmpty(form.getCenterName())) {
	        bindingResult.rejectValue("centerName", "requiredField.empty",ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE));
	    }
    	// センター名不正文字列のバリデーション
	    else if (isValidText(form.getCenterName())) {
        	bindingResult.rejectValue("centerName", "invalid.input",ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE));
        }
        // センター名超過のバリデーション
	    else if (isInvalidLength(form.getCenterName(),ModelAttributeContents.MAX_CENTER_NAME_LENGTH)) {
        	bindingResult.rejectValue("centerName", "centerName.length.wrongInput",ErrorMessageHelper.getMessage(ErrorMessage.CENTER_NAME_LENGTH_ERROR_MESSAGE));
        }
	    
	    // 郵便番号空白のバリデーション
	    else if (isNullOrEmpty(form.getPostCode())) {
	        bindingResult.rejectValue("postCode", "requiredField.empty",ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE));
	    }
        // 郵便番号超過のバリデーション
	    else if (isInvalidLength(form.getPostCode(), ModelAttributeContents.MAX_POST_CODE_LENGTH)) {
	    	bindingResult.rejectValue("postCode", "postCode.length.wrongInput",ErrorMessageHelper.getMessage(ErrorMessage.POSTCODE_LENGTH_ERROR_MESSAGE));
        }     
        // 郵便番号形式のバリデーション
	    else if (!isZipCodeHyphen(form.getPostCode())) {
	    	bindingResult.rejectValue("postCode", "postCode.invalid.input",ErrorMessageHelper.getMessage(ErrorMessage.POSTCODE_INVALID_INPUT_ERROR_MESSAGE));
        }     
	    
	    // 住所空白のバリデーション
	    else if (isNullOrEmpty(form.getAddress())) {
	        bindingResult.rejectValue("address", "requiredField.empty",ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE));
	    }
        // 住所不正文字列のバリデーション
	    else if (isValidText(form.getAddress())) {
        	bindingResult.rejectValue("address", "invalid.input",ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE));
        }   
        // 住所超過のバリデーション
	    else if (isInvalidLength(form.getAddress(), ModelAttributeContents.MAX_ADDRESS_LENGTH)) {
        	bindingResult.rejectValue("address", "address.length.wrongInpu",ErrorMessageHelper.getMessage(ErrorMessage.ADDRESS_LENGTH_ERROR_MESSAGE));
        }   
	    
	    // 電話番号空白のバリデーション
	    else if (isNullOrEmpty(form.getTelephoneNumber())) {
	        bindingResult.rejectValue("telephoneNumber", "requiredField.empty",ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE));
	    }
        // 電話番号超過のバリデーション
	    else if (isInvalidLength(form.getTelephoneNumber(), ModelAttributeContents.MAX_TELEPHONE_NUMBER_LENGTH)) {
        	bindingResult.rejectValue("telephoneNumber", "telephoneNumber.length.wrongInput",ErrorMessageHelper.getMessage(ErrorMessage.TELEPHONE_NUMBER_LENGTH_ERROR_MESSAGE));
        }   
	    
	    // 管理者名空白のバリデーション
	    else if (isNullOrEmpty(form.getAdministratorName())) {
	        bindingResult.rejectValue("administratorName", "requiredField.empty",ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE));
	    }
        // 管理者名超過のバリデーション
	    else if (isInvalidLength(form.getAdministratorName(), ModelAttributeContents.MAX_ADMINISTRATOR_NAME_LENGTH)) {
        	bindingResult.rejectValue("administratorName", "administratorName.length.wrongInput",ErrorMessageHelper.getMessage(ErrorMessage.ADMIN_NAME_LENGTH_ERROR_MESSAGE));
        }   
        // 管理者名不正文字列のバリデーション
	    else if (isValidText(form.getAdministratorName())) {
        	bindingResult.rejectValue("administratorName", "invalid.input",ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE));
        }  
	    
	    // 稼働ステータス空白のバリデーション
	    else if (isNullOrEmpty(String.valueOf(form.getOperationalStatus()))) {
	        bindingResult.rejectValue("operationalStatus", "requiredField.empty",ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE));
	    }
	    // 稼働ステータス不正文字列のバリデーション
	    else if (isInvalidOperationalStatus(form.getOperationalStatus())) {
	        bindingResult.rejectValue("operationalStatus", "requiredField.empty",ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE));
	    }
	    
        // 最大容量超過のバリデーション
	    else if (isInvalidLength(form.getMaximumCapacity(), ModelAttributeContents.MAX_MAXIMUM_CAPACITY_LENGTH)) {
        	bindingResult.rejectValue("maximumCapacity", "maximumCapacity.length.wrongInput",ErrorMessageHelper.getMessage(ErrorMessage.MAXIMUM_CAPACITY_LENGTH_ERROR_MESSAGE));
        }    

        // 現在容量超過のバリデーション
	    else if (isInvalidLength(form.getCurrentCapacity(), ModelAttributeContents.MAX_CURRENT_CAPACITY_LENGTH)) {
        	bindingResult.rejectValue("currentCapacity", "currentCapacity.length.wrongInput",ErrorMessageHelper.getMessage(ErrorMessage.CURRENT_CAPACITY_LENGTH_ERROR_MESSAGE));
        }  
        
        // 備考超過のバリデーション
	    else if (isInvalidLength(form.getRemarks(), ModelAttributeContents.MAX_REMARKS_LENGTH)) {
        	bindingResult.rejectValue("remarks", "remarks.length.wrongInput",ErrorMessageHelper.getMessage(ErrorMessage.REMARKS_LENGTH_ERROR_MESSAGE));
        }   
        // 備考不正文字列のバリデーション
	    else if (isValidText(form.getRemarks())) {
        	bindingResult.rejectValue("remarks", "invalid.input",ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE));
        }         

		// エラーメッセージをモデルに追加
		model.addAttribute(LogMessage.FLASH_ATTRIBUTE_ERROR, errorMsg.toString());

		logValidationError(LogMessage.HTTP_POST, form + " " + errorMsg.toString());
	}
	
	 /**
     * フォームの必須フィールドが空かどうかを確認
     * @param form フォームデータ
     * @return 必須のフィールドが一つでもnullまたは空の場合はtrue、それ以外はfalse
     */
    private boolean isNullOrEmpty(String input) {
        // センター名または都道府県がnullまたは空の場合にtrueを返す
        return (input== null || input.trim().isEmpty()) ;
    }
    
    /**
     * 文字列のサイズが指定された最大長を超過しているかチェックする
     * @param input チェックする文字列
     * @param maxLength 最大長
     * @return 最大長を超過している場合は true、そうでない場合は false
     */
    private boolean isInvalidLength(String input, int maxLength) {
        // 文字サイズが超過していないか確認
    	if (input != null) {
             return input.length() > maxLength;
        }

        return false;
    }
    
    /**
     * 郵便番号チェック
     * @param value 検証対象の値
     * @return true:郵便番号､false:郵便番号ではない
     */
    public static boolean isZipCodeHyphen(String value) {
        boolean result = true;

        if (value != null) {
            Pattern pattern = Pattern.compile("^[0-9]{3}-[0-9]{4}$");
            result = pattern.matcher(value).matches();
        }

        return result;
    }
    
    /**
     * 文字列の不正文字チェックを実施する
     * @param input
     * @return
     */
    private boolean isValidText(String input) {
        // 文字列の各文字を1つずつチェック
        for (char c : input.toCharArray()) {
            // 不正文字が含まれているか確認
            if (isInvalidCharacter(c)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 文字が不正文字かをチェックするメソッド
     * 
     * @param character チェックする文字
     * @return 不正文字なら true, それ以外は false
     */
    private static boolean isInvalidCharacter(char character) {
        for (InvalidCharacter invalidChar : InvalidCharacter.values()) {
            if (invalidChar.getCharacter() == character) {
            	// 不正文字が見つかった
                return true;
            }
        }
        // 不正文字ではない
        return false;
    }
    
    /**
     * 稼働ステータスが不正文字かをチェックするメソッド
     * 
     * @param character チェックする文字
     * @return 不正文字なら true, それ以外は false
     */
    private static boolean isInvalidOperationalStatus(int input) {
        return (input > ModelAttributeContents.MAX_OPERATIONAL_STATUS || input < ModelAttributeContents.MIN_OPERATIONAL_STATUS) ;
     }
}