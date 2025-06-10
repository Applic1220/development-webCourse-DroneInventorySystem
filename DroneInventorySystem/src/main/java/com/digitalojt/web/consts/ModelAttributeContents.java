package com.digitalojt.web.consts;

/**
 * 画面属性定数クラス
 *
 * @author dotlife
 * 
 */
public class ModelAttributeContents {

	// 商品情報リスト
	public static final String CENTER_INFO_LIST = "centerInfoList";
	
	// 都道府県
	public static final String REGIONS = "regions";
	
	// 操作の種類
	public static final String TYPE = "type";
	
	// 操作のステータス
	public static final String STATUS = "status";
	
	// エラーメッセージ
	public static final String ERROR_MSG = "errorMsg";
	
	// 成功メッセージ
	public static final String SUCCESS_MSG = "successMsg";
	
	/** 操作履歴画面 */
	// 操作履歴一覧
	public static final String OPERATION_LOG_LIST = "operationLogList";

	/** 在庫センター情報画面*/
	public static final int INITIAL_CAPACITY_FROM = 10;// 容量(From)初期値
    public static final int MAX_CENTER_NAME_LENGTH = 20; // センター名最大文字数
    
	/** 在庫センター情報画面新規登録*/
    public static final int MAX_POST_CODE_LENGTH = 8; //　郵便番号最大文字数
    public static final int MAX_ADDRESS_LENGTH = 255; //　住所最大文字数    
    public static final int MAX_TELEPHONE_NUMBER_LENGTH = 20; //　電話番号最大文字数   
    public static final int MAX_ADMINISTRATOR_NAME_LENGTH = 100; //　管理者名最大文字数   
    public static final int MAX_MAXIMUM_CAPACITY_LENGTH = 10; //　最大容量最大文字数
    public static final int MAX_CURRENT_CAPACITY_LENGTH = 10; //　現在容量最大文字数    
    public static final int MAX_REMARKS_LENGTH = 255; //　備考最大文字数    
    public static final int MAX_OPERATIONAL_STATUS = 1; //　稼働ステータス最大  
    public static final int MIN_OPERATIONAL_STATUS = 0; //　稼働ステータス最小 
    public static final int DELETE_FLG_TRUE = 1; //　削除フラグTRUE
    public static final int DELETE_FLG_FALSE = 0; //　削除フラグFALSE 
}
