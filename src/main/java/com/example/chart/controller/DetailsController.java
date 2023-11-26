package com.example.chart.controller;

import com.example.chart.domain.dto.DetailsDto;
import com.example.chart.service.DetailsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * (Details)表控制层
 *
 * @author ChenBaiYi
 * @since 2023-11-23 15:52:08
 */
@RestController
@RequestMapping("details")
@RequiredArgsConstructor
public class DetailsController extends BaseController {
    /**
     * 服务对象
     */
    private final DetailsService detailsService;

    /**
     * @param detailsdto 请求参数封装
     * @return 实例对象
     * @author ChenBaiYi
     * @description //TODO  分页查询所有数据
     * @date 2023-11-23 15:52:08
     */
    @PostMapping("/getDetailss")
    public TableDataInfo findDetailsSelectList(final @RequestBody DetailsDto detailsdto) {
        startPage();
        return getDataTable(this.detailsService.findDetailsSelectList(detailsdto));
    }

    /**
     * @param detailsdto 请求参数封装
     * @return 单条数据
     * @author ChenBaiYi
     * @description //TODO  通过主键查询单条数据
     * @date 2023-11-23 15:52:08
     */
    @PostMapping("/getDetailsById")
    public AjaxResult selectDetailsById(final @RequestBody DetailsDto detailsdto) {
        return AjaxResult.success(detailsService.selectDetailsById(detailsdto.getJobid()));
    }

    /**
     * @param detailsdto 实体对象
     * @return 新增结果
     * @author ChenBaiYi
     * @description //TODO  新增数据
     * @date 2023-11-23 15:52:08
     */
    @PostMapping("/addDetails")
    public AjaxResult addDetails(final @RequestBody DetailsDto detailsdto) {
        return toAjax(this.detailsService.insert(detailsdto));
    }

    /**
     * @param detailsdto 实体对象
     * @return 修改结果
     * @author ChenBaiYi
     * @description //TODO  修改数据
     * @date 2023-11-23 15:52:08
     */
    @PostMapping("/updateDetails")
    public AjaxResult update(final @RequestBody DetailsDto detailsdto) {
        return toAjax(this.detailsService.update(detailsdto));
    }

    /**
     * @param ids 主键集合
     * @return 删除结果
     * @author ChenBaiYi
     * @description //TODO  删除数据
     * @date 2023-11-23 15:52:08
     */
    @PostMapping("/deleteDetails")
    public AjaxResult delete(final @RequestParam("ids") List<Long> ids) {
        return toAjax(this.detailsService.deleteById(ids));
    }

    @PostMapping("/action.do")
    public AjaxResult findDetailsSelectListAll(final @RequestBody DetailsDto detailsdto) {
        return AjaxResult.success(detailsService.getAll());
    }
}
