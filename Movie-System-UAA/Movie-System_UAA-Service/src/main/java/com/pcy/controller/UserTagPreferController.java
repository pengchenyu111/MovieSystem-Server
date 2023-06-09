package com.pcy.controller;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.commmon.web.ErrorMessages;
import com.pcy.domain.movieTag.MovieTag;
import com.pcy.domain.movieUserTagPrefer.UserTagPrefer;
import com.pcy.service.UserTagPreferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UserTagPrefer)表控制层
 *
 * @author PengChenyu
 * @since 2021-02-03 21:32:33
 */
@RestController
@RequestMapping("/tagPrefer")
@Api(tags = "用户喜欢的电影标签接口")
public class UserTagPreferController {
    /**
     * 服务对象
     */
    @Resource
    private UserTagPreferService userTagPreferService;

    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "主键查询", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "Integer")
    })
    @GetMapping("/{userId}")
    public ApiResponse<UserTagPrefer> queryById(@PathVariable("userId") Integer userId) {
        UserTagPrefer userTagPrefer = this.userTagPreferService.queryById(userId);
        if (userTagPrefer == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, userTagPrefer);
    }

    /**
     * 通过主键查询详细用户喜好标签数据
     *
     * @param userId 主键
     * @return 标签列表数据
     */
    @ApiOperation(value = "主键查询", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "Integer")
    })
    @GetMapping("/detail/{userId}")
    public ApiResponse<List<MovieTag>> queryFullInfoById(@PathVariable("userId") Integer userId) {
        List<MovieTag> movieTagList = this.userTagPreferService.queryFullInfoById(userId);
        if (CollectionUtils.isEmpty(movieTagList)) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieTagList);
    }

    /**
     * 更新单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "更新单条", notes = "更新单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "body", name = "userTagPrefer", value = "已修改的用户喜好分类信息", required = true, dataType = "UserTagPrefer")
    })
    @PutMapping("/{userId}")
    public ApiResponse<UserTagPrefer> update(@RequestBody UserTagPrefer userTagPrefer, @PathVariable("userId") Integer userId) {
        UserTagPrefer updateInfo = this.userTagPreferService.update(userTagPrefer);
        if (updateInfo == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.CHANGE_FAIL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.CHANGE_SUCCESS, updateInfo);
    }


    /**
     * 新增单条数据
     *
     * @return 单条数据
     */
    @ApiOperation(value = "新增单条", notes = "新增单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "userTagPrefer", value = "用户喜好分类信息", required = true, dataType = "UserTagPrefer")
    })
    @PostMapping()
    public ApiResponse<UserTagPrefer> insert(@RequestBody UserTagPrefer userTagPrefer) {
        UserTagPrefer insertInfo = this.userTagPreferService.insert(userTagPrefer);
        if (insertInfo == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.CHANGE_FAIL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.CHANGE_SUCCESS, insertInfo);
    }

    /**
     * 通过主键删除单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "新增单条", notes = "新增单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "Integer")
    })
    @DeleteMapping("/{userId}")
    public ApiResponse<UserTagPrefer> update(@PathVariable("userId") Integer userId) {
        boolean isDeleted = this.userTagPreferService.deleteById(userId);
        if (!isDeleted) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.CHANGE_FAIL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.CHANGE_SUCCESS, null);
    }

}