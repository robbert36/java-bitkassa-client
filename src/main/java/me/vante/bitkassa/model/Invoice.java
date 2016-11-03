package me.vante.bitkassa.model;

/**
 * Created by robbertcoeckelbergh on 2/11/16.
 */
public class Invoice {
    private String _action;
    private String _merchant_id;
    private String _currency;
    private Double _amount;
    private String _description;
    private String _return_url;
    private String _update_url;
    private String _meta_info;

    public Invoice(Double _amount, String _currency) {
        this._amount = _amount;
        this._currency = _currency;
    }

    public Invoice(Double _amount, String _currency, String _description) {
        this._amount = _amount;
        this._currency = _currency;
        this._description = _description;
    }

    public Invoice(Double _amount, String _currency, String _description, String _return_url) {
        this._amount = _amount;
        this._currency = _currency;
        this._description = _description;
        this._return_url = _return_url;
    }

    public Invoice(Double _amount, String _currency, String _description, String _return_url, String _update_url) {
        this._amount = _amount;
        this._currency = _currency;
        this._description = _description;
        this._return_url = _return_url;
        this._update_url = _update_url;
    }

    public Invoice(Double _amount, String _currency, String _description, String _return_url, String _update_url, String _meta_info) {
        this._amount = _amount;
        this._currency = _currency;
        this._description = _description;
        this._return_url = _return_url;
        this._update_url = _update_url;
        this._meta_info = _meta_info;
    }


    //Getters and Setters
    public void set_action(String _action) {
        this._action = _action;
    }

    public void set_merchant_id(String _merchant_id) {
        this._merchant_id = _merchant_id;
    }

    public void set_currency(String _currency) {
        this._currency = _currency;
    }

    public void set_amount(Double _amount) {
        this._amount = _amount;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public void set_return_url(String _return_url) {
        this._return_url = _return_url;
    }

    public void set_update_url(String _update_url) {
        this._update_url = _update_url;
    }

    public void set_meta_info(String _meta_info) {
        this._meta_info = _meta_info;
    }

    public String get_action() {
        return _action;
    }

    public String get_merchant_id() {
        return _merchant_id;
    }

    public String get_currency() {
        return _currency;
    }

    public Double get_amount() {
        return _amount;
    }

    public String get_description() {
        return _description;
    }

    public String get_return_url() {
        return _return_url;
    }

    public String get_update_url() {
        return _update_url;
    }

    public String get_meta_info() {
        return _meta_info;
    }
}
