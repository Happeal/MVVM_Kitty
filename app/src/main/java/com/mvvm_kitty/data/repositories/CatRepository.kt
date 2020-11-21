package com.mvvm_kitty.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mvvm_kitty.data.local.entities.BreedEntity
import com.mvvm_kitty.data.local.entities.BreedImageEntity
import com.mvvm_kitty.data.remote.CatService

class CatRepository(private val catService : CatService) {


    private val mObservableBreeds: MediatorLiveData<List<BreedEntity>> = MediatorLiveData()
    private val mObservableBreedImages : MediatorLiveData<List<BreedImageEntity>> = MediatorLiveData()

    private fun getBreedsFromApi() : LiveData<List<BreedEntity>>{

        Log.d("FROM", "GETBREEDSFROMAPI")

        mObservableBreeds.addSource(catService.getAllBreeds()) {
           mObservableBreeds.postValue(it.resource?.map { breedDto ->
               breedDto.toEntity()
           })

        }

        return mObservableBreeds

    }

    private fun getBreedImagesFromApi(breed: String) : LiveData<List<BreedImageEntity>>{

            mObservableBreedImages.addSource(catService.getAllImages(breed, 10)){

                mObservableBreedImages.postValue(it.resource?.map { breedDto ->
                    breedDto.toEntity()})

            }

        return mObservableBreedImages
    }

    /**
     * Get the list of breeds from the api or other source and get notified when the data changes.
     */
    fun getBreeds(): LiveData<List<BreedEntity>>{
        return getBreedsFromApi()
    }

    fun getBreedImages(breedId: String): LiveData<List<BreedImageEntity>> {
        return getBreedImagesFromApi(breedId)
    }

}