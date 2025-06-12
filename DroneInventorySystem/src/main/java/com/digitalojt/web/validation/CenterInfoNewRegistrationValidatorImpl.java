package com.digitalojt.web.validation;

import java.util.regex.Pattern;

import com.digitalojt.web.consts.ErrorMessage;
import com.digitalojt.web.consts.InputCenterformInvalidCharacter;
import com.digitalojt.web.exception.ErrorMessageHelper;
import com.digitalojt.web.form.CenterNewRegistrationForm;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 在庫センター情報登録のバリデーション処理実装
 * CenterNewRegistrationFormのフィールドに対してバリデーションを行うクラスです。
 */
public class CenterInfoNewRegistrationValidatorImpl implements ConstraintValidator<CenterInfoNewRegistrationValidator, CenterNewRegistrationForm> {

    /**
     * フォームデータのバリデーション処理を行う
     * @param form バリデーション対象のフォームデータ
     * @param context バリデーションコンテキスト
     * @return フォームが有効かどうか（有効ならtrue、無効ならfalse）
     */
    @Override
    public boolean isValid(CenterNewRegistrationForm form, ConstraintValidatorContext context) {
        
    	// センター名に不正文字が含まれる場合にエラー処理
        if (isValidText(form.getCenterName())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }
        // 住所不正文字列のバリデーション
        if (isValidText(form.getAddress())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }   
        
        // 管理者名不正文字列のバリデーション
        if (isValidText(form.getAdministratorName())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }   
        
        // 備考不正文字列のバリデーション
        if (isValidText(form.getRemarks())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }  
                
        // バリデーションが成功した場合はtrueを返す
        return true;
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
     * 文字が不正文字かをチェックするメソッド
     * 
     * @param character チェックする文字
     * @return 不正文字なら true, それ以外は false
     */
    private static boolean isInvalidCharacter(char character) {
        for (InputCenterformInvalidCharacter invalidChar : InputCenterformInvalidCharacter.values()) {
            if (invalidChar.getCharacter() == character) {
            	// 不正文字が見つかった
                return true;
            }
        }
        // 不正文字ではない
        return false;
    }

}
