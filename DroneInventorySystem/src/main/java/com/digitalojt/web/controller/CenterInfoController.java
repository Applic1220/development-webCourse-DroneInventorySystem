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
		centerInfoService.registerNewCenter(form);
		logEnd(LogMessage.HTTP_POST);
		// 一覧ページへリダイレクト
		return "redirect:" + UrlConsts.CENTER_INFO; 
		     
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
		bindingResult.getAllErrors().forEach(error -> {
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
		bindingResult.getAllErrors().forEach(error -> {
			String message = error.getDefaultMessage();
			errorMsg.append(message).append("\r\n"); // メッセージを改行で区切って追加
		});
		
		// 対象コンポーネントにエラー情報を紐づけ
    	// センター名不正文字列のバリデーション
	    if (isValidText(form.getCenterName())) {
        	bindingResult.rejectValue("centerName", "invalid.input",ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE));
        }
        // 住所不正文字列のバリデーション
	    else if (isValidText(form.getAddress())) {
        	bindingResult.rejectValue("address", "invalid.input",ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE));
        }   
        // 管理者名不正文字列のバリデーション
	    else if (isValidText(form.getAdministratorName())) {
        	bindingResult.rejectValue("administratorName", "invalid.input",ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE));
        }  
	    
	    // 稼働ステータス不正文字列のバリデーション
	    else if (isInvalidOperationalStatus(form.getOperationalStatus())) {
	        bindingResult.rejectValue("operationalStatus", "requiredField.empty",ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE));
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