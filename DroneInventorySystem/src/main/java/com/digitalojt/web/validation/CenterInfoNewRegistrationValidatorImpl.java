package com.digitalojt.web.validation;

import java.util.regex.Pattern;

import com.digitalojt.web.consts.ErrorMessage;
import com.digitalojt.web.consts.InvalidCharacter;
import com.digitalojt.web.consts.ModelAttributeContents;
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
        
        // センター名が空である場合にエラー処理
        if (isNullOrEmpty(form.getCenterName())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }
        
    	// センター名に不正文字が含まれる場合にエラー処理
        if (isValidText(form.getCenterName())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.INVALID_INPUT_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }
        
        // センター名が指定文字数を超過する場合にエラー処理
        if (isValidLenghtCenterName(form.getCenterName())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.CENTER_NAME_LENGTH_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }
        // 郵便番号が空である場合にエラー処理
        if (isNullOrEmpty(form.getPostCode())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }
        // 郵便番号超過のバリデーション
        if (isInvalidLength(form.getPostCode(), ModelAttributeContents.MAX_POST_CODE_LENGTH)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.POSTCODE_LENGTH_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }
        
        // 郵便番号形式のバリデーション
        if (!isZipCodeHyphen(form.getPostCode())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.POSTCODE_INVALID_INPUT_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }      
        // 住所が空である場合にエラー処理
        if (isNullOrEmpty(form.getAddress())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE))
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
        
        // 住所超過のバリデーション
        if (isInvalidLength(form.getAddress(), ModelAttributeContents.MAX_ADDRESS_LENGTH)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.ADDRESS_LENGTH_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }   
        // 電話番号が空である場合にエラー処理
        if (isNullOrEmpty(form.getTelephoneNumber())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }
        // 電話番号超過のバリデーション
        if (isInvalidLength(form.getTelephoneNumber(), ModelAttributeContents.MAX_TELEPHONE_NUMBER_LENGTH)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.TELEPHONE_NUMBER_LENGTH_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }   
        
        // 管理者名が空である場合にエラー処理
        if (isNullOrEmpty(form.getAdministratorName())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }
        // 管理者名超過のバリデーション
        if (isInvalidLength(form.getAdministratorName(), ModelAttributeContents.MAX_ADMINISTRATOR_NAME_LENGTH)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.ADMIN_NAME_LENGTH_ERROR_MESSAGE))
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
        // 稼働ステータスが空である場合にエラー処理
        if (isNullOrEmpty(String.valueOf(form.getOperationalStatus()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.REQUIRED_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }
        // 最大容量超過のバリデーション
        if (isInvalidLength(form.getMaximumCapacity(), ModelAttributeContents.MAX_MAXIMUM_CAPACITY_LENGTH)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.MAXIMUM_CAPACITY_LENGTH_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }    
        
        // 現在容量超過のバリデーション
        if (isInvalidLength(form.getCurrentCapacity(), ModelAttributeContents.MAX_CURRENT_CAPACITY_LENGTH)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.CURRENT_CAPACITY_LENGTH_ERROR_MESSAGE))
                   .addConstraintViolation();
            return false;
        }  
        
        // 備考超過のバリデーション
        if (isInvalidLength(form.getRemarks(), ModelAttributeContents.MAX_REMARKS_LENGTH)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessageHelper.getMessage(ErrorMessage.REMARKS_LENGTH_ERROR_MESSAGE))
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
     * センター名文字列のサイズチェックを実施する
     * @param input
     * @return
     */
    private boolean isValidLenghtCenterName(String input) {
        // 文字サイズが超過していないか確認
        if (input.length() >ModelAttributeContents.MAX_CENTER_NAME_LENGTH) {
            return true;
         }
        return false;
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

        return true;
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
     * フォームの必須フィールドが空かどうかを確認
     * @param form フォームデータ
     * @return 必須のフィールドが一つでもnullまたは空の場合はtrue、それ以外はfalse
     */
    private boolean isNullOrEmpty(String input) {
        return (input== null || input.isEmpty()) ;
    }

}
