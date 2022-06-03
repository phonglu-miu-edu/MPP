package models;

public class ResponseModel<T> {
	private T data;
	private boolean isSuccess;
	private String errorMessage;
	
	public ResponseModel() {
		this.isSuccess = false;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.isSuccess = false;
		this.errorMessage = errorMessage;
	}
}
