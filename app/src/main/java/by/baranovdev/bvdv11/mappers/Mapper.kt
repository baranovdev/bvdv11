package by.baranovdev.bvdv11.mappers

interface Mapper<F, T> {

    fun map(from: F): T
}