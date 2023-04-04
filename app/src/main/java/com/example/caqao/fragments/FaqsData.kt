package com.example.caqao.fragments

data class FaqsData(val question : String,
                    val answer: String,
                    var isExpandable : Boolean = false)