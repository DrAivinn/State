package com.example.state


class Product(val titleID: Int){
    companion object{
        val products = listOf(
            Product(R.string.fish),
            Product(R.string.potato),
            Product(R.string.сucumber),
            Product(R.string.loaf),
            Product(R.string.meat),
            Product(R.string.banana),
        )

    }
}


