<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--/sidebar-menu-->
<div class="sidebar-menu">
	<header class="logo1">
		<a href="#" class="sidebar-icon"> <span class="fa fa-bars"></span>
		</a>
	</header>
	<div style="border-top:1px ridge rgba(255, 255, 255, 0.15)"></div>
	<div class="menu">
		<ul id="menu">
			<defined:isright rightCode="asset_menu">
				<li id="menu-academico">
					<a href="<%=path%>/projectManage/toProjectList.do"><i class="fa fa-envelope fa-desktop"></i>
						<span>项目管理</span>
						<div class="clearfix"></div>
					</a>
				</li>
			</defined:isright>
			<defined:isright rightCode="basetype_menu">
				<li>
					<a href="<%=path %>/company/toCompanyList.do"><i class="fa fa-black-tie"
						aria-hidden="true"></i><span>公司管理</span>
					<div class="clearfix"></div>
				</a>
			</li>
			</defined:isright>
			<defined:isright rightCode="site_menu">
				<li id="menu-academico">
					<a href="<%=path %>/siteManage/siteMain.do"><i
						class="fa fa-credit-card"></i><span>公司证照</span>
						<div class="clearfix"></div>
					</a>
				</li>
			</defined:isright>
			<defined:isright rightCode="project_menu">
				<li id="menu-academico"><a href="<%=path %>/product/toProductList.do"><i
						class="fa fa-product-hunt" aria-hidden="true"></i><span>产品管理</span> </span>
					<div class="clearfix"></div>
					</a>
				</li>
			</defined:isright>
			<defined:isright rightCode="assettype_menu">
				<li id="menu-academico">
					<a href="<%=path %>/assetTypes/toAssetList.do"><i
						class="fa fa-pied-piper-pp" aria-hidden="true"></i><span>产品证照</span> </span>
						<div class="clearfix"></div>
					</a>
				</li>
			</defined:isright>
			
			<defined:isright rightCode="rightParent_menu">
				<li>
					<a href="javascript:void(0);">
						<i class="fa fa-user"></i> 
						<span>权限管理</span>
						<span class="fa fa-angle-right" style="float: right;margin-top:4px;"></span>
						<div class="clearfix"></div>
					</a>
						<ul id="menu-academico-sub">
							<defined:isright rightCode="user_menu">
								<li id="menu-academico-avaliacoes"><a href="<%=path %>/user/toUserList.do">用户管理</a>
								</li>
							</defined:isright>	
							<defined:isright rightCode="role_menu">
								<li id="menu-academico-avaliacoes"><a href="<%=path %>/role/toRoleList.do">角色管理</a>
								</li>
							</defined:isright>	
							<defined:isright rightCode="right_menu">
								<li id="menu-academico-avaliacoes"><a href="<%=path %>/right/rightList.do">权限管理</a>
								</li>
							</defined:isright>	
						</ul>
				</li>
			</defined:isright>
			<defined:isright rightCode="rightParent_menu">
				<li>
					<a href="javascript:void(0);">
						<i class="fa fa-bar-chart"></i> 
						<span>报表</span>
						<span class="fa fa-angle-right" style="float: right;margin-top:4px;"></span>
						<div class="clearfix"></div>
					</a>
						<ul id="menu-academico-sub">
							<defined:isright rightCode="user_menu">
								<li id="menu-academico-avaliacoes"><a href="<%=path %>/productNearInvalid/toproductNearInvalid.do">产品证照近效期报表</a>
								</li>
							</defined:isright>	
							<defined:isright rightCode="role_menu">
								<li id="menu-academico-avaliacoes"><a href="<%=path %>/companyNearInvalid/tocompanyNearInvalid.do">公司证照近效期报表</a>
								</li>
							</defined:isright>	
							
						</ul>
				</li>
			</defined:isright>
			<defined:isright rightCode="assettype_menu">
				<li id="menu-academico">
					<a href="<%=path %>/productType/toProductTypeList.do"><i
						class="fa fa-wrench" aria-hidden="true"></i><span>产品类型</span> </span>
						<div class="clearfix"></div>
					</a>
				</li>
			</defined:isright>
			<defined:isright rightCode="assettype_menu">
				<li id="menu-academico">
					<a href="<%=path %>/licenseType/tolicenseTypeList.do"><i
						class="fa fa-gear" aria-hidden="true"></i><span>证照类型</span> </span>
						<div class="clearfix"></div>
					</a>
				</li>
			</defined:isright>
			<defined:isright rightCode="assettype_menu">
				<li id="menu-academico">
					<a href="<%=path %>/ediLog/toediLogList.do"><i
						class="fa fa-folder-o" aria-hidden="true"></i><span>EDI日志查询</span> </span>
						<div class="clearfix"></div>
					</a>
				</li>
			</defined:isright>
		</ul>
	</div>
</div>
<div class="clearfix"></div>
