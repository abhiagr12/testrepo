/**
 * All Queries used to get Category and Product attribute information from Etilize
 */
package com.valtech.test.etilize;

public final class EtilizeConstants
{
	
	public static void main(String [] arg){
		System.out.println(PRODUCT_IMAGES_QUERY);
	}
	
	
	
	private static final String LINE_BREAK = "\n";

	/*
	 * Impex Definitions
	 */

	public static final String IMPEX_DEFINITIONS = "$productCatalog=westconProductCatalog"
			+ LINE_BREAK
			+ "$productCatalogName=Westcon Product Catalog"
			+ LINE_BREAK
			+ "$catalogVersion=catalogversion(catalog(id[default=$productCatalog]),version[default='Online'])[unique=true,default=$productCatalog:Online]"
			+ LINE_BREAK + "$supercategories=supercategories(code, $catalogVersion)" + LINE_BREAK
			+ "$categorySupercategories=source(code, $catalogVersion)[unique=true]" + LINE_BREAK
			+ "$categories=target(code, $catalogVersion)[unique=true]" + LINE_BREAK + "$lang=en" + LINE_BREAK
			+ "$approved=approvalstatus(code)[default='approved']" + LINE_BREAK;

	/*
	 * Impex Headers
	 */

	// Categories
	public static final String CATEGORY_INSERT_UPDATE_HEADER = "INSERT_UPDATE Category;code[unique=true];name[lang=$lang];$catalogVersion";
	public static final String CATEGORY_HIERARCHY_INSERT_UPDATE_HEADER = "INSERT_UPDATE CategoryCategoryRelation;$categories;$categorySupercategories";

	// Products
	public static final String PRODUCT_MAIN_DATA_INITIAL_UPDATE_HEADER = "UPDATE Product;code[unique=true];unspsc;etilizeProductId;name[$lang];description[$lang];existsInEtilize[default=true];$catalogVersion";
	public static final String PRODUCT_MAIN_DATA_DELTA_UPDATE_HEADER = "UPDATE Product;code[unique=true];name[$lang];description[$lang];existsInEtilize[default=true];$catalogVersion";
	public static final String PRODUCT_CATEGORIES_BRAND_NAME_NEW_AND_DELTA_UPDATE_HEADER = "UPDATE Product;code[unique=true];brandName;$supercategories;$catalogVersion;existsInEtilize[default=true]";

	/*
	 * Query parameters
	 */

	public static final String DAYS_BEFORE_PARAM = "$daysBefore$";
	public static final String PRODUCT_CODE_PARAM = "$productCode$";

	/*
	 * MSSQL SERVER Stored Results Queries
	 */

	public static final String All_WESTCON_CATEGORY_HIERARCHY_QUERY = "WITH category_children_list ( categoryid, categoryname, parentcategoryid, parentcategoryname, catlevel )"
			+ "AS"
			+ "("
			+ "	SELECT c.categoryid, cn.name, c.parentcategoryid, cpn.name, c.catlevel"
			+ "	FROM category c"
			+ "	JOIN category AS cp ON c.categoryid = cp.categoryid AND cp.isactive = 1"
			+ "	JOIN categorynames AS cn ON cn.categoryid = c.categoryid AND cn.localeid = 1"
			+ "	LEFT JOIN categorynames AS cpn ON cpn.categoryid = c.parentcategoryid AND cpn.localeid = 1"
			+ "	WHERE c.categoryid IN"
			+ "	("
			+ "		SELECT c2.categoryid"
			+ "		FROM category c2 "
			+ "		WHERE c2.isactive = 1"
			+ "		AND EXISTS"
			+ "		("
			+ "			SELECT * FROM product AS p"
			+ "			LEFT JOIN productskus AS ps ON p.productid = ps.productid AND ps.name ='Westcon Group' AND ps.isactive=1"
			+ "			WHERE p.categoryid = c2.categoryid"
			+ "		)"
			+ "	)"
			+ "	UNION ALL"
			+ "	SELECT c.categoryid, ccn.name, c.parentcategoryid, cpn.name, c.catlevel"
			+ "	FROM category AS c"
			+ "	JOIN category_children_list AS ccl ON c.categoryid = ccl.parentcategoryid"
			+ "	JOIN categorynames AS ccn ON c.categoryid = ccn.categoryid AND ccn.localeid = 1"
			+ "	JOIN categorynames AS cpn ON c.parentcategoryid = cpn.categoryid AND cpn.localeid = 1"
			+ "	WHERE c.isactive = 1"
			+ ")"
			+ "("
			+ "SELECT c.catlevel, c.categoryid, cn.name as 'categoryname', c.parentcategoryid, cpn.name as 'parentcategoryname' "
			+ "FROM category AS c "
			+ "JOIN categorynames AS cn ON c.categoryid = cn.categoryid AND cn.localeid = 1 "
			+ "LEFT JOIN categorynames AS cpn ON c.parentcategoryid = cpn.categoryid AND cpn.localeid = 1 "
			+ "WHERE c.isactive = 1 "
			+ "AND c.parentcategoryid IS NULL "
			+ ")"
			+ "UNION ALL"
			+ "("
			+ "SELECT ccl.catlevel, ccl.categoryid, ccl.categoryname, ccl.parentcategoryid, ccl.parentcategoryname "
			+ "FROM category_children_list AS ccl "
			+ "GROUP BY catlevel, categoryid, parentcategoryid, categoryname, parentcategoryname "
			+ ")"
			+ "ORDER BY catlevel, parentcategoryname, categoryname ";

	public static final String ALL_WESTCON_BRAND_NAMES_QUERY = "SELECT CAST([displayvalue] AS NVARCHAR(MAX)) AS 'brandname', 'Vendors Super Template' as parentcategoryid "
			+ "FROM product AS p "
			+ "JOIN productskus AS ps1 ON p.productid = ps1.productid AND ps1.isactive=1 AND ps1.name = 'Westcon Group' "
			+ "JOIN productlocales AS pl ON p.productid = pl.productid AND pl.localeid=1 AND pl.isactive=1 "
			+ "LEFT JOIN category AS c ON p.categoryid = c.categoryid AND c.isactive = 1 "
			+ "LEFT JOIN categorydisplayattributes AS cda ON c.categoryid = cda.categoryid AND cda.templatetype = 1 "
			+ "JOIN attributenames AS an ON cda.attributeid = an.attributeid AND an.localeid = 1 AND an.name = 'Brand Name' "
			+ "LEFT JOIN productattribute AS pa ON p.productid = pa.productid AND cda.attributeid = pa.attributeid AND pa.localeid = 1 AND pa.isactive=1 "
			+ "WHERE p.isactive = 1 "
			+ "AND datalength(RTRIM(LTRIM(ISNULL(CAST([displayvalue] AS NVARCHAR(MAX)), '')))) > 0 "
			+ "GROUP BY CAST([displayvalue] AS NVARCHAR(MAX)) "
			+ "UNION ALL "
			+ "SELECT 'Vendors Super Template', '' "
			+ "ORDER BY parentcategoryid, brandname ";


	public static final String PRODUCTS_ORDER_BY_CODE = "ORDER BY ps1.sku";

	public static final String WESTCON_PRODUCTS_MAIN_DATA_QUERY = "SELECT ps1.productid AS 'etilizeProductId', ps1.sku AS 'code', ps2.sku AS 'unspsc', pd2.description AS 'name', pd3.description AS 'description' "
			+ "FROM product AS p "
			+ "JOIN productskus AS ps1 ON p.productid = ps1.productid AND ps1.isactive=1 AND ps1.name = 'Westcon Group' "
			+ "JOIN productlocales AS pl ON p.productid = pl.productid AND pl.localeid=1 AND pl.isactive=1 "
			+ "LEFT JOIN productskus AS ps2 ON p.productid = ps2.productid AND ps2.isactive=1 and ps2.name = 'UNSPSC' "
			+ "LEFT JOIN productdescriptions AS pd2 ON p.productid = pd2.productid AND pd2.isactive = 1 AND pd2.localeid=1 AND pd2.type = 2 "
			+ "LEFT JOIN productdescriptions AS pd3 ON p.productid = pd3.productid AND pd3.isactive = 1 AND pd3.localeid=1 AND pd3.type = 3 "
			+ "WHERE p.isactive = 1 ";

	public static final String WESTCON_PRODUCT_CATEGORIES_QUERY = "SELECT ps1.productid AS 'etilizeProductId', ps1.sku AS 'code', cn.categoryid, cn.name as 'categoryname' "
			+ "FROM product AS p "
			+ "JOIN productskus AS ps1 ON p.productid = ps1.productid AND ps1.isactive=1 AND ps1.name = 'Westcon Group' "
			+ "JOIN productlocales AS pl ON p.productid = pl.productid AND pl.localeid=1 AND pl.isactive=1 "
			+ "JOIN category AS c ON p.categoryid = c.categoryid AND c.isactive = 1 "
			+ "JOIN categorynames AS cn ON c.categoryid = cn.categoryid AND cn.localeid = 1 " + "WHERE p.isactive = 1 ";

	public static final String WESTCON_PRODUCT_BRAND_NAMES_QUERY = "SELECT ps1.sku AS 'code', CAST([displayvalue] AS NVARCHAR(MAX)) AS 'brandname' "
			+ "FROM product AS p "
			+ "JOIN productskus AS ps1 ON p.productid = ps1.productid AND ps1.isactive=1 AND ps1.name = 'Westcon Group' "
			+ "JOIN productlocales AS pl ON p.productid = pl.productid AND pl.localeid=1 AND pl.isactive=1 "
			+ "LEFT JOIN category AS c ON p.categoryid = c.categoryid AND c.isactive = 1 "
			+ "LEFT JOIN categorynames AS cn ON c.categoryid = cn.categoryid AND cn.localeid = 1 "
			+ "LEFT JOIN categorydisplayattributes AS cda ON c.categoryid = cda.categoryid AND cda.templatetype = 1 "
			+ "JOIN attributenames AS an ON cda.attributeid = an.attributeid AND an.localeid = 1 AND an.name = 'Brand Name' "
			+ "LEFT JOIN productattribute AS pa ON p.productid = pa.productid AND cda.attributeid = pa.attributeid AND pa.localeid = 1 AND pa.isactive=1 "
			+ "WHERE p.isactive = 1 " + "AND datalength(RTRIM(LTRIM(ISNULL(CAST([displayvalue] AS NVARCHAR(MAX)), '')))) > 0 ";

	// Parameter beforeDays
	public static final String CREATION_DATE_FILTER = "AND p.creationdate ";
	public static final String MODIFIED_DATE_FILTER = "AND p.modifieddate ";

	public static final String BETWEEN_DATES_CONDITION_WITH_PARAMETER = "BETWEEN DATEADD(DAY, -" + DAYS_BEFORE_PARAM
			+ ", CAST(FLOOR(CAST(GETDATE() AS FLOAT)) AS DATETIME)) AND "
			+ "    DATEADD(DAY, 1, CAST(FLOOR(CAST(GETDATE() AS FLOAT)) AS DATETIME)) ";

	// Initial Import of Products
	public static final String INITIAL_WESTCON_PRODUCTS_MAIN_DATA_QUERY = WESTCON_PRODUCTS_MAIN_DATA_QUERY
			+ PRODUCTS_ORDER_BY_CODE;

	public static final String INITIAL_WESTCON_PRODUCTS_CATEGORIES_QUERY = WESTCON_PRODUCT_CATEGORIES_QUERY
			+ PRODUCTS_ORDER_BY_CODE;

	public static final String INITIAL_WESTCON_PRODUCTS_BRAND_NAME_QUERY = WESTCON_PRODUCT_BRAND_NAMES_QUERY
			+ PRODUCTS_ORDER_BY_CODE;

	// Delta Import of Products - New Data
	public static final String DELTA_WESTCON_NEW_PRODUCTS_MAIN_DATA_QUERY = WESTCON_PRODUCTS_MAIN_DATA_QUERY
			+ CREATION_DATE_FILTER + BETWEEN_DATES_CONDITION_WITH_PARAMETER + PRODUCTS_ORDER_BY_CODE;

	public static final String DELTA_WESTCON_NEW_PRODUCT_CATEGORIES_QUERY = WESTCON_PRODUCT_CATEGORIES_QUERY
			+ CREATION_DATE_FILTER + BETWEEN_DATES_CONDITION_WITH_PARAMETER + PRODUCTS_ORDER_BY_CODE;

	public static final String DELTA_WESTCON_NEW_PRODUCT_BRAND_NAMES_QUERY = WESTCON_PRODUCT_BRAND_NAMES_QUERY
			+ CREATION_DATE_FILTER + BETWEEN_DATES_CONDITION_WITH_PARAMETER + PRODUCTS_ORDER_BY_CODE;

	// Delta Import of Products - Modified Data
	public static final String DELTA_WESTCON_MODIFIED_PRODUCTS_MAIN_DATA_QUERY = WESTCON_PRODUCTS_MAIN_DATA_QUERY
			+ MODIFIED_DATE_FILTER + BETWEEN_DATES_CONDITION_WITH_PARAMETER + PRODUCTS_ORDER_BY_CODE;

	public static final String DELTA_WESTCON_MODIFIED_PRODUCT_CATEGORIES_QUERY = WESTCON_PRODUCT_CATEGORIES_QUERY
			+ MODIFIED_DATE_FILTER + BETWEEN_DATES_CONDITION_WITH_PARAMETER + PRODUCTS_ORDER_BY_CODE;

	public static final String DELTA_WESTCON_MODIFIED_PRODUCT_BRAND_NAMES_QUERY = WESTCON_PRODUCT_BRAND_NAMES_QUERY
			+ MODIFIED_DATE_FILTER + BETWEEN_DATES_CONDITION_WITH_PARAMETER + PRODUCTS_ORDER_BY_CODE;


	public static final String CATEGORY_LAST_MODIFICATION_DATE_QUERY = "SELECT modified FROM categorydelta WHERE modified "
			+ BETWEEN_DATES_CONDITION_WITH_PARAMETER;

	/*
	 * MSSQL SERVER Run Time Import Queries
	 */

	public static final String PRODUCT_IMAGES_QUERY = "SELECT p.productid, pe.type, pe.status, pep.propertyvalue AS 'maxSize' "
			+ "FROM product AS p "
			+ "LEFT JOIN productelements as pe ON p.productid = pe.productid AND pe.isactive = 1 AND pe.localeid = 1 "
			+ "LEFT JOIN productelementproperties pep ON pe.productelementid = pep.productelementid AND pep.propertykey = 'max-size' "
			+ "LEFT JOIN productskus AS productSkus ON p.productid = productSkus.productid AND productSkus.name ='Westcon Group' AND productSkus.isactive=1 "
			+ "WHERE p.isactive = 1 "
			// Excludes 'Front', 'Bottom', 'Left', 'Rear', 'Right' , 'Top', otherwise it will be duplicated with the Maximum type associated (FrontMaximum for example) "
			// i.e: Only Front available is not possible, but only FrontMaximum is possible "
			+ "AND type in ('Main', 'FrontMaximum', 'BottomMaximum', 'LeftMaximum', 'RearMaximum', 'RightMaximum' , 'TopMaximum') "
			+ "AND productskus.sku = '" + PRODUCT_CODE_PARAM + "' " + "Order BY pe.type";

	public static final String PRODUCT_OVERVIEW_TECHNICAL_SPECIFICATION_CONTENT_QUERY = "SELECT cn.categoryid, cn.name AS categoryname, cda.templatetype, ch.displayorder AS headerOrder, chn.headerid, chn.name as headername, cda.displayorder AS attributeOrder, an.attributeid, an.name AS attributename, CAST([displayvalue] AS NVARCHAR(MAX)) AS attributevalue "
			+ "FROM categorydisplayattributes AS cda "
			+ "LEFT JOIN category AS c ON cda.categoryid = c.categoryid AND c.isactive = 1 "
			+ "LEFT JOIN categoryheader ch ON cda.categoryid = ch.categoryid AND cda.headerid = ch.headerid AND cda.templatetype = ch.templatetype "
			+ "LEFT JOIN headernames as chn ON ch.headerid = chn.headerid and chn.localeid = 1 "
			+ "LEFT JOIN categorynames AS cn ON c.categoryid = cn.categoryid AND cn.localeid = 1 "
			+ "LEFT JOIN attributenames AS an ON cda.attributeid = an.attributeid AND an.localeid = 1 "
			+ "LEFT JOIN product AS p ON c.categoryid = p.categoryid AND p.isactive = 1 "
			+ "LEFT JOIN productlocales AS pl ON p.productid = pl.productid AND pl.localeid=1 AND pl.status<>'Accessory' AND pl.isactive=1 "
			+ "LEFT JOIN productskus AS ps ON p.productid = ps.productid AND ps.name ='Westcon Group' AND ps.isactive=1 "
			+ "LEFT JOIN productattribute AS pa ON p.productid = pa.productid AND cda.attributeid = pa.attributeid AND pa.localeid = 1 AND pa.isactive=1 "
			+ "WHERE cda.isactive = 1 "
			// Removing General Information (headerid = 35) from the template type = 0 to build the Overview tab */ "
			+ "AND ( (cda.templatetype = 0 AND cda.headerid != 35) OR (cda.templatetype = 1)) "
			// Remove attributes with empty display values
			+ "AND datalength(RTRIM(LTRIM(ISNULL(CAST([displayvalue] AS NVARCHAR(MAX)), '')))) > 0 "
			+ "AND ps.sku = '"
			+ PRODUCT_CODE_PARAM
			+ "' "
			+ "GROUP BY cn.categoryid, cn.name, cda.templatetype, ch.displayorder, chn.headerid, chn.name, cda.displayorder, an.attributeid, an.name, CAST([displayvalue] AS NVARCHAR(MAX)) "
			+ "ORDER BY cn.name, cda.templatetype, ch.displayorder, cda.displayorder";

	public static final String PRODUCT_OPTIONS_ACCESSORIES_QUERY = "SELECT pa.productid AS 'etilizeProductId', productSkus.sku as 'productcode', pa.accessoryproductid, accessorySkus.sku as 'accessorycode', pd.description AS accessoryname "
			+ "FROM productaccessories AS pa "
			+ "LEFT JOIN product AS product ON pa.productid = product.productid AND product.isactive = 1 "
			+ "LEFT JOIN product AS accessory ON pa.accessoryproductid = accessory.productid AND accessory.isactive = 1 "
			+ "JOIN productlocales AS productLocales ON pa.productid = productLocales.productid AND productLocales.localeid=1 AND productLocales.isactive=1 "
			+ "JOIN productlocales AS accessoryLocales ON pa.accessoryproductid = accessoryLocales.productid AND accessoryLocales.localeid=1 AND accessoryLocales.isactive=1 "
			+ "LEFT JOIN productskus AS productSkus ON pa.productid = productSkus.productid AND productSkus.name ='Westcon Group' AND productSkus.isactive=1 "
			+ "LEFT JOIN productskus AS accessorySkus ON pa.accessoryproductid = accessorySkus.productid AND accessorySkus.name ='Westcon Group' AND accessorySkus.isactive=1 "
			+ "LEFT JOIN productdescriptions AS pd ON pa.accessoryproductid = pd.productid AND pd.localeid=1 AND pd.isactive=1 AND pd.type = 1 "
			+ "WHERE pa.localeid=1 AND pa.isactive = 1 "
			+ "AND productSkus.sku = '"
			+ PRODUCT_CODE_PARAM
			+ "' "
			+ "ORDER BY accessorySkus.sku";

	public static final String PRODUCT_DOCUMENTS_QUERY = "SELECT p.productid AS 'etilizeProductId', productskus.sku, pe.type, pe.status  "
			+ "FROM product AS p "
			+ "JOIN productelements AS pe ON p.productid = pe.productid AND pe.isactive = 1 AND pe.localeid = 1 "
			+ "LEFT JOIN productskus AS productSkus ON p.productid = productSkus.productid AND productSkus.name ='Westcon Group' AND productSkus.isactive=1 "
			+ "WHERE p.isactive = 1 "
			+ "AND type in ('Manufacturer-Brochure', 'Assembly-Instructions', 'User-Manual', 'Energy-Guide', 'Educational-Brochure') "
			+ "AND productskus.sku = '" + PRODUCT_CODE_PARAM + "' " + " ORDER BY pe.type";

	/*
	 * Queries Return Fields
	 */

	// Categories
	public static final String CATEGORY_CODE_FIELD = "categoryid";
	public static final String CATEGORY_NAME_FIELD = "categoryname";
	public static final String CATEGORY_PARENT_FIELD = "parentcategoryid";

	// Products
	public static final String PRODUCT_CODE_FIELD = "code";
	public static final String ETILIZE_PRODUCT_ID_FIELD = "etilizeProductId";
	public static final String PRODUCT_UNSPSC_FIELD = "unspsc";
	public static final String PRODUCT_NAME_FIELD = "name";
	public static final String PRODUCT_DESCRIPTION_FIELD = "description";
	public static final String BRAND_NAME_FIELD = "brandname";
	public static final String PRODUCT_SUPERCATEGORIES_FIELD = "supercategories";

	// Product Images
	public static final String PRODUCT_IMAGE_MAX_SIZE_FIELD = "maxSize";

	// Product Overview and Technical Specifications Headers
	public static final String PRODUCT_TEMPLATE_TYPE_FIELD = "templatetype";
	public static final String PRODUCT_OVERVIEW_TEMPLATE_TYPE_VALUE = "0";
	public static final String PRODUCT_TECHNICAL_SPECIFICATIONS_TEMPLATE_TYPE_VALUE = "1";
	public static final String PRODUCT_HEADER_NAME_FIELD = "headername";
	public static final String PRODUCT_HEADER_ID_FIELD = "headerid";
	public static final String PRODUCT_ATTRIBUTE_NAME_FIELD = "attributename";
	public static final String PRODUCT_ATTRIBUTE_VALUE_FIELD = "attributevalue";

	// Product Options and Accessories
	public static final String PRODUCT_ACCESSORY_ID_FIELD = "accessoryproductid";
	public static final String PRODUCT_ACCESSORY_CODE_FIELD = "accessorycode";
	public static final String PRODUCT_ACCESSORY_NAME_FIELD = "accessoryname";

	// Product Documents
	public static final String PRODUCT_DOCUMENT_TYPE_FIELD = "type";

	// Auxiliary Fields
	public static final String CATALOG_VERSION_FIELD = "catalogVersion";
}
