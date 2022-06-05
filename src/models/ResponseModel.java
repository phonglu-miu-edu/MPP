package models;

public class ResponseModel<T> {
	private T data;
	private boolean isSuccess;
	private String errorMessage;
	
	public ResponseModel() {
		this.isSuccess = true;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.isSuccess = false;
		this.errorMessage = errorMessage;
	}

	public boolean getIsSuccess() {
		return this.isSuccess;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}
}
