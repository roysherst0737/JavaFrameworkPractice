package tw.idv.william.web.member.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import tw.idv.william.web.member.dao.MemberDao;
import tw.idv.william.web.member.entity.Member;

@Repository
public class MemberDaoImpl implements MemberDao {
	@PersistenceContext
	private Session session;

	@Override
	public int insert(Member member) {
		session.persist(member);
		return 1;
	}

	@Override
	public int deleteById(Integer id) {
		Member member = new Member();
		member.setId(id);
//		session.remove(member); 這樣會出現java.lang.IllegalArgumentException: Removing a detached instance
	/**
	 * session.remove() works only on entities which are managed in the current transaction/context. 
	 * In your case, you're retrieving the entity in an earlier transaction, 
	 * storing it in the HTTP session and then attempting to remove it in a different transaction/context.
	 * This just won't work.
	 */
		session.remove(session.contains(member) ? member : session.merge(member));
		return 1;
	}

	@Override
	public int update(Member member) {
		final StringBuilder hql = new StringBuilder()
				.append("UPDATE Member SET ");
		final String password = member.getPassword();
		if (password != null && !password.isEmpty()) {
			hql.append("password = :password,");
		}
		final byte[] img = member.getImage();
		if (img != null && img.length != 0) {
			hql.append("image = :image,");
		}
		hql.append("nickname = :nickname,")
			.append("pass = :pass,")
			.append("roleId = :roleId,")
			.append("updater = :updater,")
			.append("lastUpdatedDate = NOW() ")
			.append("WHERE username = :username");
		Query query = session.createQuery(hql.toString());
		if (password != null && !password.isEmpty()) {
			query.setParameter("password", member.getPassword());
		}
		if (img != null && img.length != 0) {
			query.setParameter("image", img);
		}
		return query.setParameter("nickname", member.getNickname())
				.setParameter("pass", member.getPass())
				.setParameter("roleId", member.getRoleId())
				.setParameter("updater", member.getUpdater())
				.setParameter("username", member.getUsername())
				.executeUpdate();
	}

	@Override
	public Member selectById(Integer id) {
		return session.get(Member.class, id);
	}

	@Override
	public List<Member> selectAll() {
		final String hql = "FROM Member ORDER BY id";
		return session
				.createQuery(hql, Member.class)
				.list();
	}

	@Override
	public Member selectByUsername(String username) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));
		return session.createQuery(criteriaQuery).uniqueResult();
	}

	@Override
	public Member selectForLogin(String username, String password) {
		final String sql = "select * from MEMBER "
				+ "where USERNAME = :username and PASSWORD = :password";
		return session.createNativeQuery(sql, Member.class)
				.setParameter("username", username)
				.setParameter("password", password)
				.uniqueResult();
	}
}
