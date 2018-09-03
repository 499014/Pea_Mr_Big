package com.its.peac1.pea_mr_big;

import android.widget.EditText;

/**
 * Created by 499014 on 20/11/2560.
 */

public class Item_Register {
    Integer EditTextId ;
    String Register_Code ,Check_Operation ,Eror_max ,Error_min , Check_state,Max_read,Min_read ,BEFORE_READ ,AFTER_DOT,BEFORE_DOT ,INPUT ;
    EditText Edti_Rigister ;


    public Item_Register(Integer editTextId, String register_Code, String check_Operation, String eror_max, String error_min, String check_state, String max_read, String min_read,EditText Edti_Rigister,String BEFORE_READ ,String AFTER_DOT ,String BEFORE_DOT ,String INPUT) {
        EditTextId = editTextId;
        Register_Code = register_Code;
        Check_Operation = check_Operation;
        Eror_max = eror_max;
        Error_min = error_min;
        Check_state = check_state;
        Max_read = max_read;
        Min_read = min_read;
        this.BEFORE_READ = BEFORE_READ ;
        this.Edti_Rigister = Edti_Rigister;
        this.AFTER_DOT =AFTER_DOT ;
        this.BEFORE_DOT=BEFORE_DOT ;
        this.INPUT = INPUT ;
    }

    public String getINPUT() {
        return INPUT;
    }

    public void setINPUT(String INPUT) {
        this.INPUT = INPUT;
    }

    public void setAFTER_DOT(String AFTER_DOT) {
        this.AFTER_DOT = AFTER_DOT;
    }

    public void setBEFORE_DOT(String BEFORE_DOT) {
        this.BEFORE_DOT = BEFORE_DOT;
    }

    public String getAFTER_DOT() {
        return AFTER_DOT;
    }

    public String getBEFORE_DOT() {
        return BEFORE_DOT;
    }

    public String getBEFORE_READ() {
        return BEFORE_READ;
    }

    public void setBEFORE_READ(String BEFORE_READ) {
        this.BEFORE_READ = BEFORE_READ;
    }

    public EditText getEdti_Rigister() {
        return Edti_Rigister;
    }

    public void setEdti_Rigister(EditText edti_Rigister) {
        Edti_Rigister = edti_Rigister;
    }

    public Integer getEditTextId() {
        return EditTextId;
    }

    public String getRegister_Code() {
        return Register_Code;
    }

    public String getCheck_Operation() {
        return Check_Operation;
    }

    public String getEror_max() {
        return Eror_max;
    }

    public String getError_min() {
        return Error_min;
    }

    public String getCheck_state() {
        return Check_state;
    }

    public String getMax_read() {
        return Max_read;
    }

    public String getMin_read() {
        return Min_read;
    }

    public void setEditTextId(Integer editTextId) {
        EditTextId = editTextId;
    }

    public void setRegister_Code(String register_Code) {
        Register_Code = register_Code;
    }

    public void setCheck_Operation(String check_Operation) {
        Check_Operation = check_Operation;
    }

    public void setEror_max(String eror_max) {
        Eror_max = eror_max;
    }

    public void setError_min(String error_min) {
        Error_min = error_min;
    }

    public void setCheck_state(String check_state) {
        Check_state = check_state;
    }

    public void setMax_read(String max_read) {
        Max_read = max_read;
    }

    public void setMin_read(String min_read) {
        Min_read = min_read;
    }
}
