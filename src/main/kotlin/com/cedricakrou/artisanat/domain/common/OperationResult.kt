package com.cedricakrou.artisanat.domain.common

class OperationResult<T> ( val errors : Map<String, String>, val data : T)