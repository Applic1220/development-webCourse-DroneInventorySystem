package com.digitalojt.web.consts;

/**
 * エラーメッセージ定数クラス
 * 
 * @author dotlife
 *
 */
public class ErrorMessage {
	
	// ログイン情報の入力に誤りがあった場合に、出力するエラーメッセージのID
	public static final String  LOGIN_WRONG_INPUT = "login.wrongInput";

	// データが空の場合のエラーメッセージ
	public static final String DATA_EMPTY_ERROR_MESSAGE = "data.empty";
	
	// すべての項目が空の場合のエラーメッセージ
	public static final String ALL_FIELDS_EMPTY_ERROR_MESSAGE = "allField.empty";

	// 空文字検索に関するエラーメッセージ
	public static final String UNEXPECTED_INPUT_ERROR_MESSAGE = "unexpected.input";

	// 不正な文字列を使用した検索に関するエラーメッセージ
	public static final String INVALID_INPUT_ERROR_MESSAGE = "invalid.input";

	// センター名文字超過に関するエラーメッセージ
	public static final String CENTER_NAME_LENGTH_ERROR_MESSAGE = "centerName.length.wrongInput";
	
	// 郵便番号文字超過に関するエラーメッセージ
	public static final String POSTCODE_LENGTH_ERROR_MESSAGE = "postCode.length.wrongInput";
	
	// 郵便番号形式不一致に関するエラーメッセージ
	public static final String POSTCODE_FORMAT_INPUT_ERROR_MESSAGE = "postCode.format.wrongInput";
	
	// 住所文字超過に関するエラーメッセージ
	public static final String ADDRESS_LENGTH_ERROR_MESSAGE = "address.length.wrongInpu";
	
	// 電話番号文字超過に関するエラーメッセージ
	public static final String TELEPHONE_NUMBER_LENGTH_ERROR_MESSAGE = "telephoneNumber.length.wrongInput";
	
	// 管理者名文字超過に関するエラーメッセージ
	public static final String ADMIN_NAME_LENGTH_ERROR_MESSAGE = "administratorName.length.wrongInput";
	
	// 最大容量文字超過に関するエラーメッセージ
	public static final String MAXIMUM_CAPACITY_LENGTH_ERROR_MESSAGE = "maximumCapacity.length.wrongInput";
	
	// 現在容量名文字超過に関するエラーメッセージ
	public static final String CURRENT_CAPACITY_LENGTH_ERROR_MESSAGE = "currentCapacity.length.wrongInput";
	
	// 備考名文字超過に関するエラーメッセージ
	public static final String REMARKS_LENGTH_ERROR_MESSAGE = "remarks.length.wrongInput";
	
	// 必須項目未入力に関するエラーメッセージ
	public static final String REQUIRED_ERROR_MESSAGE = "requiredField.empty";
	
	// 空欄の場合のエラーメッセージキー
	public static final String CATEGORY_NAME_REQUIRED = "category.name.required";
	
	// 禁止文字チェック（{ } ; = $ & ）が含まれている場合のエラーメッセージキー
	public static final String CATEGORY_NAME_FORBIDDEN = "category.name.forbidden";
	
	// 文字数が制限を超えた場合のエラーメッセージキー
	public static final String CATEGORY_NAME_INVALID_LENGTH = "category.name.length";
	
	// 操作履歴画面の操作時刻に関するエラーメッセージ
	public static final String OPERATION_DATE_FIELD_ERROR_MESSAGE = "operationLog.operationDateField.empty";
	
}
