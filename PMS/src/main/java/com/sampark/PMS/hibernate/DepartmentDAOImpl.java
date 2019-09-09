package com.sampark.PMS.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sampark.PMS.PMSConstants;
import com.sampark.PMS.dto.Department;
import com.sampark.PMS.dto.DepartmentName;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;

public class DepartmentDAOImpl implements DepartmentDAO {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public ResponseObject saveDepartment(RequestObject res) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx =session.beginTransaction();
		try {
			Department object = new Department();
			if(res.getId() == null)
			{	
				object.setStatus(PMSConstants.STATUS_ACTIVE);
			    object.setCreatedOn(new Date());
			    object.setCreatedBy(CommonUtils.getCurrentUserName());
			    object.setName(res.getName());
			    object.setDescription(res.getDescription());
			    session.persist(object);
			}
			else
			{
			    object = (Department) session.get(Department.class, res.getId());
			    object.setModifiedOn(new Date());
			    object.setModifiedBy(CommonUtils.getCurrentUserName());
			    object.setName(res.getName());
			    object.setDescription(res.getDescription());
				session.update(object);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllDepartmentList());
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while adding new Department ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new Department ... " + e);
			responseObject.setObject(e);
		}finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}
	@Override
	public List<Department> getAllDepartmentList() {
		Session session = this.sessionFactory.openSession();
		List<Department> list = new ArrayList<Department>();
		try {
			Criteria criteria = session.createCriteria(Department.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting Department List... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public ResponseObject deleteDepartment(Integer departmentId) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Department department = (Department) session.get(Department.class, departmentId);
			department.setStatus(PMSConstants.STATUS_DELETED);
			session.update(department);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllDepartmentList());
			responseObject.setString("Successfully delete");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while deleting Department ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while deleting Department ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentName> getAllDepartmentIdList() {
		Session session = this.sessionFactory.openSession();
		List<DepartmentName> idList = new ArrayList<DepartmentName>();
		try {
			String sql = "select dep.ID as id, dep.NAME as name from department as dep where dep.STATUS =:STATUS";
			Query query = session.createSQLQuery(sql).addEntity(DepartmentName.class);
			query.setParameter("STATUS", PMSConstants.STATUS_ACTIVE);
			idList = query.list(); 
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting all Department Id's List ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return idList;
	}
}
