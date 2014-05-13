package com.pz.xingfutao.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pz.xingfutao.entities.ImageMap;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.entities.OrderEntity;
import com.pz.xingfutao.entities.UserEntity;

public class XFDatabase extends SQLiteOpenHelper{
	
	private static final String DB_NAME = "xft.db";
	private static final int DB_VERSION = 2;
	
	private int cartCount = -1;
	private int favCount = -1;
	private int orderCount = -1;
	
	private final class User{
		public static final String NAME = "user_table";
		
		public static final String ID = "id";
		public static final String USER_NAME = "user_name";
		public static final String AVATAR = "avatar";
		public static final String PASSWORD = "password";
		public static final String GENDER = "gender";
		public static final String ADDR = "address";
		public static final String EMAIL = "email";
		public static final String PHONE_NUMBER = "phone_number";
		public static final String RECIPIENT = "recipient";
	}
	
	private final class Cart{
		public static final String NAME = "cart_table";

		public static final String ID = "id";
		public static final String GOOD_ID = "good_id";
		public static final String GOOD_THUMB = "good_thumb";
		public static final String PURCHASE_COUNT = "purchase_count";
		public static final String PRICE = "price";
		public static final String GOOD_NAME = "good_name";
		public static final String TIMESTAMP = "timestamp";
		
	}
	
	private final class Fav{
		public static final String NAME = "fav_table";

		public static final String ID = "id";
		public static final String GOOD_ID = "good_id";
		public static final String GOOD_THUMB = "good_thumb";

	}
	
	private final class Order{
		public static final String NAME = "order_table";

		public static final String ID = "id";
		public static final String GOOD_ID = "good_id";
		public static final String GOOD_NAME = "good_name";
		public static final String GOOD_THUMB = "god_thumb";
		public static final String PURCHASE_COUNT = "purchase_count";
		public static final String TIME_STAMP = "time_stamp";
		public static final String ADDR = "address";
		public static final String PHONE_NUM = "phone_number";
		public static final String RECIPIENT = "recipient";

	}
	
	private static XFDatabase instance;
	
	public synchronized static XFDatabase getInstance(Context context){
		if(instance == null){
			instance = new XFDatabase(context);
		}
		
		return instance;
	}

	private XFDatabase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + User.NAME + " (" + 
	                                 User.ID + " INTEGER PRIMARY KEY," + 
				                     User.USER_NAME + " TEXT," + 
	                                 User.AVATAR + " TEXT," + 
	                                 User.PASSWORD + " TEXT," + 
				                     User.GENDER + " TEXT," + 
	                                 User.ADDR + " TEXT," + 
				                     User.PHONE_NUMBER + " TEXT," + 
	                                 User.EMAIL + " TEXT," + 
				                     User.RECIPIENT + " TEXT);");
		db.execSQL("CREATE TABLE " + Cart.NAME + " (" + 
	                                 Cart.ID + " INTEGER PRIMARY KEY," + 
				                     Cart.GOOD_ID + " TEXT," + 
	                                 Cart.PRICE + " FLOAT," + 
	                                 Cart.GOOD_THUMB + " TEXT," + 
				                     Cart.PURCHASE_COUNT + " INTEGER," + 
	                                 Cart.GOOD_NAME + " TEXT);");
		
		db.execSQL("CREATE TABLE " + Fav.NAME + " (" + 
				                     Fav.ID + " INTEGER PRIMARY KEY," + 
				                     Fav.GOOD_ID + " TEXT," + 
		                             Fav.GOOD_THUMB + " TEXT);");
		
		db.execSQL("CREATE TABLE " + Order.NAME + " (" + 
				                     Order.ID + " INTEGER PRIMARY KEY," + 
				                     Order.GOOD_ID + " TEXT," + 
				                     Order.GOOD_NAME + " TEXT," + 
				                     Order.GOOD_THUMB + " TEXT," + 
                                     Order.PURCHASE_COUNT + " TEXT," + 
				                     Order.PHONE_NUM + " TEXT," + 
                                     Order.TIME_STAMP + " INTEGER," + 
				                     Order.ADDR + " TEXT," + 
                                     Order.RECIPIENT + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + User.NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Cart.NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Fav.NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Order.NAME);
		
		onCreate(db);
	}
	
	public int getCartCountByGoodId(String goodId){
		synchronized(this){
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT * FROM " + Cart.NAME + " WHERE " + Cart.GOOD_ID + "='" + goodId + "';", null);
			if(cursor.moveToFirst()){
				return cursor.getInt(cursor.getColumnIndex(Cart.PURCHASE_COUNT));
			}
		}
		
		return -1;
	}
	
	public int getCartCount(){
		if(cartCount == -1){
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + Cart.NAME, null);
			
			if(cursor.moveToFirst()){
				cartCount = cursor.getInt(0);
				
				if(!cursor.isClosed()) cursor.close();
			}
		}
		
		return cartCount;
	}
	
	public int getFavCount(){
		if(favCount == -1){
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + Fav.NAME, null);
			
			if(cursor.moveToFirst()){
				favCount = cursor.getInt(0);
				
				if(!cursor.isClosed()) cursor.close();
			}
		}
		
		return favCount;
	}

	public int getOrderCount(){
		if(orderCount == -1){
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + Order.NAME, null);
			cursor.moveToFirst();
			
			orderCount = cursor.getInt(0);
			
			if(!cursor.isClosed()) cursor.close();
		}
		
		return orderCount;
	}
	
	public void insertUser(UserEntity entity){
		ContentValues cv = new ContentValues();
		cv.put(User.USER_NAME, entity.getName());
		cv.put(User.AVATAR, entity.getAvatar());
		cv.put(User.PASSWORD, entity.getPassword());
		cv.put(User.GENDER, entity.getGender());
		cv.put(User.EMAIL, entity.getEmail());
		cv.put(User.PHONE_NUMBER, entity.getPhoneNumber());
		cv.put(User.ADDR, entity.getAddress());
		cv.put(User.RECIPIENT, entity.getRecipient());
		
		SQLiteDatabase db = getWritableDatabase();
		db.insert(User.NAME, null, cv);
	}
	
	public void updateUser(UserEntity entity){
		synchronized(this){
			deleteUser(entity);
			
			insertUser(entity);
		}
	}
	
	public void deleteUser(UserEntity entity){
		synchronized(this){
			SQLiteDatabase db = getWritableDatabase();
			db.execSQL("DELETE FROM " + User.NAME + " WHERE " + User.USER_NAME + "='" + entity.getName() + "';");
		}
	}
	
	public OrderEntity[] getOrderList(){
		synchronized(this){
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT * FROM " + Order.NAME + ";", null);
			cursor.moveToFirst();
			OrderEntity[] result = new OrderEntity[cursor.getCount()];
			for(int i = 0; i < result.length; i++){
				result[i] = new OrderEntity();
				result[i].setGoodId(cursor.getString(cursor.getColumnIndex(Order.GOOD_ID)));
				result[i].setGoodName(cursor.getString(cursor.getColumnIndex(Order.GOOD_NAME)));
				result[i].setGoodThumb(cursor.getString(cursor.getColumnIndex(Order.GOOD_THUMB)));
				result[i].setPhoneNumber(cursor.getString(cursor.getColumnIndex(Order.PHONE_NUM)));
				result[i].setRecipient(cursor.getString(cursor.getColumnIndex(Order.RECIPIENT)));
				result[i].setAddress(cursor.getString(cursor.getColumnIndex(Order.ADDR)));
				result[i].setTimeStamp(cursor.getLong(cursor.getColumnIndex(Order.TIME_STAMP)));
				
				cursor.moveToNext();
			}
			
			if(!cursor.isClosed()) cursor.close();
			
			return result;
		}
	}
	
	public ItemDetailEntity[] getCartList(){
		synchronized(this){
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT * FROM " + Cart.NAME + ";", null);
			cursor.moveToFirst();
			ItemDetailEntity[] result = new ItemDetailEntity[cursor.getCount()];
			for(int i = 0; i < result.length; i++){
				result[i] = new ItemDetailEntity();
				result[i].setId(cursor.getString(cursor.getColumnIndex(Cart.GOOD_ID)));
				result[i].setThumb(cursor.getString(cursor.getColumnIndex(Cart.GOOD_THUMB)));
				result[i].setPurchaseCount(cursor.getInt(cursor.getColumnIndex(Cart.PURCHASE_COUNT)));
				result[i].setName(cursor.getString(cursor.getColumnIndex(Cart.GOOD_NAME)));
				result[i].setShopPrice(cursor.getFloat(cursor.getColumnIndex(Cart.PRICE)));
				
				cursor.moveToNext();
			}
			
			if(!cursor.isClosed()) cursor.close();
			
			return result;
		}
	}
	
	public void insertCart(ItemDetailEntity entity, int count){
		
		if(getCartCountByGoodId(entity.getId()) == -1){
			ContentValues cv = new ContentValues();
			cv.put(Cart.GOOD_ID, entity.getId());
			cv.put(Cart.GOOD_NAME, entity.getName());
			cv.put(Cart.GOOD_THUMB, entity.getImage());
			cv.put(Cart.PURCHASE_COUNT, count);
			cv.put(Cart.PRICE, entity.getShopPrice());
			
			SQLiteDatabase db = getWritableDatabase();
			if(db.insert(Cart.NAME, null, cv) != -1){
				cartCount++;
			}
		}else{
			SQLiteDatabase db = getWritableDatabase();
			db.execSQL("UPDATE " + Cart.NAME + " SET " + Cart.PURCHASE_COUNT + "=" + Cart.PURCHASE_COUNT + " + " + count + " WHERE " + Cart.GOOD_ID + "='" + entity.getId() + "';");
		}
		
	}
	
	public void deleteCartByGoodId(String goodId){
		synchronized(this){
			SQLiteDatabase db = getWritableDatabase();
			db.execSQL("DELETE FROM " + Cart.NAME + " WHERE " + Cart.GOOD_ID + "='" + goodId + "';");
		}
	}
	
	public void insertFav(ImageMap imageMap){
		if(imageMap.getLinkType() == ImageMap.LINK_GOOD_DETAIL){
			ContentValues cv = new ContentValues();
			cv.put(Fav.GOOD_ID, imageMap.getLink());
			cv.put(Fav.GOOD_THUMB, imageMap.getImageLink());
			
			SQLiteDatabase db = getWritableDatabase();
			if(db.insert(Fav.NAME, null, cv) != -1){
				favCount++;
			}
		}
	}
	
	public void insertOrder(OrderEntity entity){
		ContentValues cv = new ContentValues();
		cv.put(Order.GOOD_ID, entity.getGoodId());
		cv.put(Order.GOOD_NAME, entity.getGoodName());
		cv.put(Order.GOOD_THUMB, entity.getGoodThumb());
		cv.put(Order.PURCHASE_COUNT, entity.getPurchaseCount());
		cv.put(Order.TIME_STAMP, entity.getTimeStamp());
		cv.put(Order.ADDR, entity.getAddress());
		cv.put(Order.PHONE_NUM, entity.getPhoneNumber());
		cv.put(Order.RECIPIENT, entity.getRecipient());
		
		SQLiteDatabase db = getWritableDatabase();
		if(db.insert(Order.NAME, null, cv) != -1){
			orderCount++;
		}
	}
	
	public void updateCartCountByGoodId(String goodId, int count){
		synchronized(this){
			SQLiteDatabase db = getWritableDatabase();
			db.execSQL("UPDATE " + Cart.NAME + " SET " + Cart.PURCHASE_COUNT + "=" + count + " WHERE " + Cart.GOOD_ID + "='" + goodId + "';");
		}
		
	}
	
	public void updateCartCountById(int id, int count){
		synchronized(this){
			SQLiteDatabase db = getWritableDatabase();
			db.execSQL("UPDATE " + Cart.NAME + " SET " + Cart.PURCHASE_COUNT + "=" + count + " WHERE " + Cart.ID + "=" + id + ";");
		}
	}
	
	public void deleteFavByGoodId(String goodId){
		synchronized(this){
			SQLiteDatabase db = getWritableDatabase();
			db.execSQL("DELETE FROM " + Fav.NAME + " WHERE " + Fav.GOOD_ID + "='" + goodId + "';");
			favCount--;
		}
	}
	
	public void deleteFavById(int id){
		synchronized(this){
			SQLiteDatabase db = getWritableDatabase();
			db.execSQL("DELETE FROM " + Fav.NAME + " WHERE " + Fav.ID + "=" + id + ";");
			favCount--;
		}
	}
	
	public void deleteOrderById(int id){
		synchronized(this){
			SQLiteDatabase db = getWritableDatabase();
			db.execSQL("DELETE FROM " + Order.NAME + " WHERE " + Order.ID + "=" + id + ";");
			orderCount--;
		}
	}
}
