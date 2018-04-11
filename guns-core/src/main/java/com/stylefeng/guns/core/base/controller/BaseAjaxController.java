///*
// * FileName: BaseMobileAjaxController.java
//
// *
//
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// *
// * author  : Administrator
// * date     : 2014-6-8 下午3:17:27
// * last modify author :
// * version : 1.0
// */
///**
// * <p>Title: BaseMobileAjaxController.java
// * <p>Description:
//
//
// * @author blakequ Blakequ@gmail.com
// * @date 2014-6-8
// * @version 1.0
// */
//package com.stylefeng.guns.core.base.controller;
//
//import com.alibaba.fastjson.JSONObject;
//
//import com.stylefeng.guns.core.base.service.IBaseService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.Serializable;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// *
// * <p>
// * Title: BaseAjaxController.java
// * <p>
// * Description: 基本的BaseAjax方式的数据处理
// * <p>
// * Copyright: (C) 2016 youedata Tech. Co. Ltd. All Rights Reserved
// * <piaomiaoxingz@126.com>
// * <p>
// * Company: www.youedata.com
// *
// * @author piaomiao piaomiaoxingz@126.com
// * @date 2016年4月5日
// * @version 1.0
// */
//@Controller
//public abstract class BaseAjaxController<T>{
//
//	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
//
//
//
//	/**
//	 * <p>
//	 * Title: getBaseService
//	 * <p>
//	 * Description: 获得基础service
//	 *
//	 * @return
//	 */
//	public abstract IBaseService<T> getBaseService();
//
//	/**
//	 *
//	 * <p>
//	 * Title: detail
//	 * <p>
//	 * Description:通过id查询数据详情
//	 *
//	 * @author piaomiao piaomiaoxingz@126.com
//	 * @date 2016年4月5日
//	 */
//	@RequestMapping(value = "detail")
//	public void detail() {
//	}
//
//	/**
//	 *
//	 * <p>
//	 * Title: add
//	 * <p>
//	 * Description:添加或修改数据，如果数据有id传入表示修改
//	 *
//	 * @author piaomiao piaomiaoxingz@126.com
//	 * @date 2016年4月5日
//	 * @param t
//	 * @param model
//	 * @param request
//	 * @param response
//	 * @param session
//	 */
//	@RequestMapping(value = "add")
//	public void add(T t, Map model, HttpServletRequest request,
//                    HttpServletResponse response, HttpSession session) {
//		response.setHeader("Cache-Control", "no-cache");
//		long id = getBaseService().saveOrUpdate(t);
//
//
//	}
//
//	/**
//	 * 先做验证处理
//	 * @param t
//	 * @param isUpdate
//	 * @param request
//	 * @param response
//	 */
//	public String beforeAddToValidate(T t, boolean isUpdate,
//                                      HttpServletRequest request, HttpServletResponse response){
//		String swith = LoadValidateProperties.load("swith", null);
//		try{
//			if("on".equals(swith)){
//				String prefix = isUpdate==true?"update":"add";
//				Map errorMsg = BaseValidate.validate(t, prefix);
//				List<String> msg = (List<String>) errorMsg.get("totalErrorMsg");
//				if(msg.toString().length() > 2){
//					return msg.toString();
//				}else{
//					return null;
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 不做验证的controller可以走这个分支绕过验证
//	 * @param t
//	 * @param model
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "addNotVali")
//	public void addForPassValidate(T t, Map model, HttpServletRequest request, HttpServletResponse response){
//		response.setHeader("Cache-Control", "no-cache");
//		boolean isUpdate = (t.getId() != null && t.getId() > 0);
//		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_W);
//		long id = getBaseService().saveOrUpdate(t);
//		if (id > 0) {
//			if (isUpdate) {
//				model.put(KeyWords.MSG, SysValue.UPDATE_SUCCESS_MSG);
//			} else {
//				model.put(KeyWords.MSG, SysValue.ADD_SUCCESS_MSG);
//			}
//		}
//		writeOkMsg(request, response, SysValue.DEFAULT_SUCCESS_MSG);
//		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
//	}
//
//	/**
//	 *
//	 * <p>
//	 * Title: delete
//	 * <p>
//	 * Description:通过id进行删除操作
//	 *
//	 * @author piaomiao piaomiaoxingz@126.com
//	 * @date 2016年4月5日
//	 * @param request
//	 * @param response
//	 * @param model
//	 */
//	@RequestMapping(value = "delete")
//	public void delete(HttpServletRequest request,
//                       HttpServletResponse response, Map model) {
//		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_W);
//		long id = getBaseService().delete(PageUtil.getLongId(request));
//		JSONObject jsonObject = new JSONObject();
//		if (id > 0) {
//			writeOkMsg(request, response, SysValue.DEFAULT_SUCCESS_MSG);
//		} else {
//			writeFailMsg(request, response, SysValue.DEFAULT_FAIL_MSG);
//		}
//		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
//	}
//
//	/**
//	 *
//	 * <p>
//	 * Title: listByModel
//	 * <p>
//	 * Description:通过类进行数据查询,参数为类中的属性
//	 *
//	 * @author piaomiao piaomiaoxingz@126.com
//	 * @date 2016年4月5日
//	 * @param t
//	 * @param model
//	 * @param request
//	 * @param response
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "listByModel")
//	public void listByModel(T t, Map model, HttpServletRequest request,
//                            HttpServletResponse response, HttpSession session) {
//		@SuppressWarnings("rawtypes")
//		Map map = new HashMap();
//		BaseListDomain<T> baseListDomain = selectListByModelUtil(t, request,
//				response, session);
//		map.put(KeyWords.PAGE, baseListDomain.getPage());
//		map.put(KeyWords.ENTITIES, baseListDomain.getEntities());
//		writeOkMap(request, response, map);
//	}
//
//	/**
//	 * <p>
//	 * Title: selectAll
//	 * <p>
//	 * Description:查询所有有效的数据
//	 *
//	 * @param t
//	 * @param model
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "selectAll")
//	public void selectAll(T t, Map model, HttpServletRequest request,
//			HttpServletResponse response) {
//		Map map = new HashMap();
//		if (t.getIsDeleted() == null) {
//			t.setIsDeleted(false);
//		}
//		map.put(KeyWords.ENTITIES,
//				getBaseService().selectPage(t, 0, Integer.MAX_VALUE));
//		writeOkMap(request, response, map);
//	}
//
//	/**
//	 * 固定调用段Page page=new Page(cpage, totalRows, ParsUtils.getpNum(request));
//	 * model.putAll(page.putPageProperties()); 该段是实现分页相关数据显示的 里面参数有
//	 * 当前页(currentPage)、上一页(previousPage)、
//	 * 下一页(nextPage)、总页数(totalPages)、总记录条数(totalRows) 参数为params
//	 *
//	 *            <p>
//	 *            Title: listByMap
//	 *            <p>
//	 *            Description:
//	 * @author piaomiao piaomiaoxingz@126.com
//	 * @date 2016年4月5日
//	 * @param model
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "listByMap")
//	public void listByMap(Map model, HttpServletRequest request,
//                          HttpServletResponse response, HttpSession session) {
//		Map<String, Object> params = MapTools.getParamsFromReq(request);
//		BaseListDomain<T> baseListDomain = selectListByMapUtil(params, request,
//				response, session);
//		Map map = new HashMap();
//		map.put(KeyWords.PAGE, baseListDomain.getPage());
//		map.put(KeyWords.ENTITIES, baseListDomain.getEntities());
//		writeOkMap(request, response, map);
//	}
//
//	/**
//	 *
//	 * <p>
//	 * Title: search
//	 * <p>
//	 * Description:检索入口，参数是map，为params相关的
//	 *
//	 * @author piaomiao piaomiaoxingz@126.com
//	 * @date 2016年4月5日
//	 * @param model
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping("search")
//	public void search(Map model, HttpServletRequest request,
//                       HttpServletResponse response, HttpSession session) {
//		response.setHeader("Cache-Control", "no-cache");
//		Map<String, Object> params = MapTools.getParamsFromReq(request);
//		BaseListDomain<T> baseListDomain = selectListByMapUtil(params, request,
//				response, session);
//		Map map = new HashMap();
//		map.put(KeyWords.PAGE, baseListDomain.getPage());
//		map.put(KeyWords.ENTITIES, baseListDomain.getEntities());
//		MapTools.setModelParams(map, params);
//		this.writeOkMap(request, response, map);
//	}
//
//	/**
//	 *
//	 * <p>
//	 * Title: lOverviewByMap
//	 * <p>
//	 * Description:传入参数为Map，通过map查询简化版的的相应json
//	 *
//	 * @author piaomiao piaomiaoxingz@126.com
//	 * @date 2016年4月5日
//	 * @param request
//	 * @param response
//	 * @param session
//	 */
//	@RequestMapping("lOverviewByMap")
//	public void lOverviewByMap(HttpServletRequest request,
//                               HttpServletResponse response, HttpSession session) {
//		response.setHeader("Cache-Control", "no-cache");
//		Map<String, Object> params = MapTools.getParamsFromReq(request);
//		BaseListDomain<T> baseListDomain = selectListByMapUtil(params, request,
//				response, session);
//		Map map = new HashMap();
//		map.put(KeyWords.PAGE, baseListDomain.getPage());
//		map.put(KeyWords.ENTITIES,
//				createEntitiesArray(baseListDomain.getEntities(), request));
//		writeOkMap(request, response, map);
//	}
//
//	/**
//	 *
//	 * <p>
//	 * Title: getOverviewListByModel
//	 * <p>
//	 * Description: 通过model检索部分定义字段
//	 *
//	 * @param t
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping("lOverviewByModel")
//	public void getOverviewListByModel(T t, HttpServletRequest request,
//                                       HttpServletResponse response, HttpSession session) {
//		response.setHeader("Cache-Control", "no-cache");
//		Map map = new HashMap();
//		BaseListDomain<T> baseListDomain = selectListByModelUtil(t, request, response, session);
//		map.put(KeyWords.PAGE, baseListDomain.getPage());
//		map.put(KeyWords.ENTITIES, createEntitiesArray(baseListDomain.getEntities(), request));
//		writeOkMap(request, response, map);
//	}
//
//	/**
//	 *
//	 * <p>
//	 * Title: createEntitiesArray
//	 * <p>
//	 * Description:批量删除
//	 *
//	 * @author wanyongzhi wanyongzhi@youedata.com
//	 * @date 2016年4月8日
//	 * @param entities
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "batchDelete")
//	public void batchDelete(String ids, T t, HttpServletRequest request,
//                            HttpServletResponse response, HttpSession session) {
//		List<Serializable> tmpeids = com.youedata.cd.base.common.str.StringUtil
//				.StringtoList(ids);
//		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_W);
//		getBaseService().batchDelete(tmpeids);
//		Map map = new HashMap();
//		this.writeOkMap(request, response, map);
//		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
//	}
//
//	/**
//	 *
//	 * <p>
//	 * Title: createEntitiesArray
//	 * <p>
//	 * Description:反射方式处理相关类，生成对应的的类数据
//	 *
//	 * @author piaomiao piaomiaoxingz@126.com
//	 * @date 2016年4月5日
//	 * @param entities
//	 * @param request
//	 * @return
//	 */
//	public List createEntitiesArray(List<T> entities, HttpServletRequest request) {
//		List array = new ArrayList();
//		try {
//			if (CollectionUtils.isEmpty(entities)) {
//				return array;
//			}
//			for (T t : entities) {
//				String clzName = t.getClass().getName();
//				PojoTemplate pojoTemplate = PojoTemplateTools
//						.getPojoTemplate(clzName);
//				if (null != pojoTemplate) {
//					List<Method> invokeMethods = pojoTemplate
//							.getInvokeJsonMethods();
//					if (CollectionUtils.isEmpty(invokeMethods)) {
//						array.add(t);
//					} else {
//						JSONObject json = createJson(t, pojoTemplate, request);
//						if (null != json) {
//							array.add(json);
//						}
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			LOGGER.error("反射错误", e);
//		}
//		return array;
//	}
//
//
//
//	/**
//	 *
//	 * <p>
//	 * Title: selectListByModelUtil
//	 * <p>
//	 * Description:基础类，通过t的方式进行查询，返回一个带有page的t对象数据
//	 *
//	 * @author piaomiao piaomiaoxingz@126.com
//	 * @date 2016年4月13日
//	 * @param t
//	 * @param request
//	 * @param response
//	 * @param session
//	 * @return
//	 */
//	public BaseListDomain<T> selectListByModelUtil(T t,
//                                                   HttpServletRequest request, HttpServletResponse response,
//                                                   HttpSession session) {
//		if (t.getIsDeleted() == null) {
//			t.setIsDeleted(false);
//		}
//		// 通过相关条件查询总数
//		long totalRows = getBaseService().selectCount(t);
//		Page page = new Page(PageUtil.getcPg(request), totalRows,
//				PageUtil.getLimit(request));
//		page.setLastList(BaseSessionTools.getPageUrl(session));
//		// 查询列表
//		List<T> entities = getBaseService().selectPage(t,
//				(page.getCurrentPage() - 1), PageUtil.getLimit(request));
//		return new BaseListDomain<T>(page, entities);
//	}
//
//	/**
//	 *
//	 * <p>
//	 * Title: selectListByMapUtil
//	 * <p>
//	 * Description:工具类，通过map查询出相关结果
//	 *
//	 * @author piaomiao piaomiaoxingz@126.com
//	 * @date 2016年4月13日
//	 * @param params
//	 * @param request
//	 * @param response
//	 * @param session
//	 * @return
//	 */
//	public BaseListDomain<T> selectListByMapUtil(Map<String, Object> params,
//                                                 HttpServletRequest request, HttpServletResponse response,
//                                                 HttpSession session) {
//		if (!params.containsKey(KeyWords.IS_DELETED)) {
//			params.put(KeyWords.IS_DELETED, false);
//		}
//		// 通过相关条件查询总数
//		long totalRows = getBaseService().selectByMapCount(params);
//		Page page = new Page(PageUtil.getcPg(request), totalRows,
//				PageUtil.getLimit(request));
//		page.setLastList(BaseSessionTools.getPageUrl(session));
//		List<T> entities = getBaseService().selectByMapPage(params,
//				(page.getCurrentPage() - 1), PageUtil.getLimit(request));
//		return new BaseListDomain<T>(page, entities);
//	}
//
////	@InitBinder
////	public void initBinder(ServletRequestDataBinder binder) {
////		/**
////		 * 自动转换日期类型的字段格式
////		 */
//////		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
////
////		/**
////		 * 防止XSS攻击
////		 */
////		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
////	}
//}
