package demo.kotlinboilerplate.user.user.controller

import demo.kotlinboilerplate.common.exception.ExceptionEnum
import demo.kotlinboilerplate.common.pageable.PageableDto
import demo.kotlinboilerplate.common.swagger.SwaggerApiExceptionResponse
import demo.kotlinboilerplate.common.swagger.SwaggerApiResponse
import demo.kotlinboilerplate.user.user.dto.UserPatchDto
import demo.kotlinboilerplate.user.user.dto.UserResponseDto
import demo.kotlinboilerplate.user.user.dto.UserSearchConditionDto
import demo.kotlinboilerplate.user.user.mapper.UserMapper
import demo.kotlinboilerplate.user.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Min
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User")
@RestController
@RequestMapping("user")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper,
) {
    @Operation(summary = "사용자 정보 조회")
    @SwaggerApiResponse(responseCode = "200", description = "조회 성공")
    @SwaggerApiExceptionResponse(exceptions = [ExceptionEnum.NOT_FOUND_USER])
    @GetMapping("{id}")
    fun findUser(
        @Parameter(name = "id", description = "사용자 ID", example = "1")
        @PathVariable id: Long,
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userMapper.toDto(userService.findOne(id)))
    }

    @Operation(summary = "사용자 조회 - 페이징")
    @SwaggerApiResponse(responseCode = "200", description = "조회 성공")
    @SwaggerApiExceptionResponse(exceptions = [ExceptionEnum.NOT_FOUND_USER])
    @GetMapping("page")
    fun findPage(
        @Parameter(description = "조회 페이지", example = "1", required = true)
        @RequestParam(required = true, defaultValue = "1")
        @Min(1) page: Int,
        @Parameter(description = "페이지 데이터 수", example = "10", required = true)
        @RequestParam(required = true, defaultValue = "10")
        @Min(1) size: Int,
        @Parameter(description = "정렬 조건", example = "desc")
        @RequestParam(required = false, defaultValue = "desc") order: String,
        @Parameter(description = "정렬 컬럼", example = "id")
        @RequestParam(required = false, defaultValue = "id") orderBy: String,
        @ParameterObject condition: UserSearchConditionDto,
    ): ResponseEntity<PageableDto<List<UserResponseDto>>> {
        return ResponseEntity.ok(userService.findPage(page, size, order, orderBy, condition))
    }

    @Operation(summary = "사용자 수정")
    @SwaggerApiResponse(responseCode = "200", description = "수정 성공")
    @SwaggerApiExceptionResponse(exceptions = [ExceptionEnum.NOT_FOUND_USER])
    @PatchMapping("{id}")
    fun update(
        @Parameter(name = "id", description = "사용자 ID", example = "1")
        @PathVariable id: Long,
        @RequestBody requestDto: UserPatchDto,
    ) {
        userService.update(id, requestDto)
    }

    @Operation(summary = "사용자 삭제")
    @SwaggerApiResponse(responseCode = "200", description = "삭제 성공")
    @SwaggerApiExceptionResponse(exceptions = [ExceptionEnum.NOT_FOUND_USER])
    @DeleteMapping("{id}")
    fun delete(
        @Parameter(name = "id", description = "사용자 ID", example = "1")
        @PathVariable id: Long,
    ) {
        userService.delete(id)
    }
}
