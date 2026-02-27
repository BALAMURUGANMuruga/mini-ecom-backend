package com.bala.miniecom.model;

	public class StockUpdateRequest {
	    private String itemId;
	    public String getItemId() {
			return itemId;
		}
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		public int getStockQty() {
			return stockQty;
		}
		public void setStockQty(int stockQty) {
			this.stockQty = stockQty;
		}
		private int stockQty;
	}
