package com.bondidos.clevertecfinal.domain.useCases

import com.bondidos.clevertecfinal.domain.entities.api_model.postResult.PostResult
import com.bondidos.clevertecfinal.domain.repository.Repository
import com.bondidos.clevertecfinal.domain.entities.ui_model.PostForm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostFormUseCase @Inject constructor(
    private val repository: Repository
    ) {
    suspend operator fun invoke(postForm: PostForm): Flow<PostResult> = flow{
         val response = repository.postForm(postForm)
        emit(response)
    }
}