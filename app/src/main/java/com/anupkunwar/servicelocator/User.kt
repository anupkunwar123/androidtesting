package com.anupkunwar.servicelocator

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable

interface User: Observable{
    @get:Bindable
    var firstName:String
    @get:Bindable
    var lastName: String
}

object UserImpl : User,BaseObservable() {

    @get:Bindable
    override var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }
        get() = field

    @get:Bindable
    override var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }
        get() = field


}