package com.example.toko_kerudung.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import com.example.toko_kerudung.model.Customer;
import com.example.toko_kerudung.model.Kerudung;
import com.example.toko_kerudung.model.Transaksi_krdg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.toko_kerudung.model.Admin;

@Controller
public class TokoKerudungController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getUser() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String login(@ModelAttribute(name = "Admin") Admin admin, Model model) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        try {
            String sql = "SELECT * FROM admin WHERE username = ?";
            Admin asli = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Admin.class), username);
            model.addAttribute("asli", asli);
            String userAsli = asli.getUsername();
            String passAsli = asli.getPassword();
            if (password.equals(passAsli)) {
                return "redirect:/main";
            }
        } catch (EmptyResultDataAccessException e) {
            // TODO: handle exception
            model.addAttribute("invalidCredentials", true);
        }
        model.addAttribute("invalidCredentials", true);
        return "login";
    }

    @GetMapping("/main")
    public String index(Model model) {
        String sql = "SELECT * FROM customer WHERE hapus='tidak'";
        List<Customer> customerList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Customer.class));
        model.addAttribute("customer", customerList);
        return "main";
    }

    @GetMapping("/kerudung")
    public String adminList(Model model) {
        String sql = "SELECT * FROM kerudung";
        List<Kerudung> kerudungList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Kerudung.class));
        model.addAttribute("kerudung", kerudungList);
        return "kerudung";
    }

    @GetMapping("/search")
    public String search(@PathParam("nama_customer") String nama_customer, Model model) {
        String sql = "SELECT * FROM customer WHERE LOWER(nama_customer) LIKE CONCAT(CONCAT ('%', ?), '%')";
        List<Customer> customerData = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Customer.class), nama_customer);
        model.addAttribute("customer", customerData);
        return ("search");
    }

    @GetMapping("/softdeletecustomer/{id_customer}")
    public String softDelete(@PathVariable("id_customer") String id_customer) {
        String sql = "UPDATE customer SET hapus = 'ya' WHERE id_customer = ?";
        jdbcTemplate.update(sql, id_customer);
        return "redirect:/main";
    }

    @GetMapping("/restore/{id_customer}")
    public String restore(@PathVariable("id_customer") String id_customer) {
        String sql = "UPDATE customer SET hapus = 'tidak' WHERE id_customer = ?";
        jdbcTemplate.update(sql, id_customer);
        return "redirect:/main";
    }

    @GetMapping("/sampah")
    public String recycle(Model model) {
        String sql = "SELECT * FROM customer WHERE hapus='ya'";
        List<Customer> customerList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Customer.class));
        model.addAttribute("customer", customerList);
        return "sampah";
    }

    @GetMapping("/harddelete/{id_customer}")
    public String harddelete(@PathVariable("id_customer") String id_customer) {
        String sql = "DELETE FROM customer WHERE id_customer = ?";
        jdbcTemplate.update(sql, id_customer);
        return "redirect:/main";
    }

    @GetMapping("/addcustomer")
    public String addCustomer(Model model) {
        return "addcustomer";
    }

    @RequestMapping(value ="/addcustomer")
    public String addCustomer(Customer customer, Model model) {
        String sql = "INSERT INTO customer VALUES (?, ?, ?, ?, 'tidak')";
        jdbcTemplate.update(sql,
                customer.getId_customer(),customer.getNama_customer(), customer.getNo_telp(), customer.getAlamat());
        return "redirect:/main";
    }

    @GetMapping("/editcustomer/{id_customer}")
    public String editCustomer(@PathVariable("id_customer") String id_customer, Model model) {
        String sql = "SELECT * FROM customer WHERE id_customer = ?";
        Customer customer = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Customer.class), id_customer);
        model.addAttribute("customer", customer);
        return "editcustomer";
    }

    @PostMapping("/editcustomer")
    public String editCustomer(Customer customer) {
        String sql = "UPDATE customer SET id_customer=?, nama_customer = ?, no_telp = ?, alamat = ? WHERE id_customer = ?";
        jdbcTemplate.update(sql, customer.getId_customer(),customer.getNama_customer(),customer.getNo_telp(),customer.getAlamat(),customer.getId_customer());
        return "redirect:/main";
    }

    @GetMapping("/transaksi_krdg/{id_customer}")
    public String transaksiList(@PathVariable("id_customer") String id_customer, Model model) {
        String sql = "SELECT * FROM transaksi_krdg WHERE id_customer = ?";
        List<Transaksi_krdg> transaksiList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Transaksi_krdg.class), id_customer);
        model.addAttribute("transaksi_krdg", transaksiList);
        return "transaksi_krdg";
    }

    @GetMapping("/addtransaksi")
    public String addTransaksi(Model model) {
        return "addtransaksi";
    }

    @RequestMapping(value ="/addtransaksi")
    public String addTransaksi(Transaksi_krdg transaksi_krdg, Model model) {

        String sql = "INSERT INTO transaksi_krdg VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, transaksi_krdg.getId_transaksi(), transaksi_krdg.getJumlah(), transaksi_krdg.getTgl_trans(), transaksi_krdg.getId_customer(), transaksi_krdg.getId_kerudung());
        return "redirect:/main";
    }

    @GetMapping("/harddeletetransaksi/{id_transaksi}")
    public String harddeletetransaksi(@PathVariable("id_transaksi") String id_transaksi) {
        String sql = "DELETE FROM transaksi_krdg WHERE id_transaksi = ?";
        jdbcTemplate.update(sql, id_transaksi);
        return "redirect:/main";
    }

    @GetMapping("/detailtransaksikerudung/{id_transaksi}")
    public String detailtransaksi(@PathVariable("id_transaksi") String id_transaksi, Model model) {
        String sql = "SELECT * FROM transaksi_krdg JOIN kerudung ON (transaksi_krdg.id_kerudung = kerudung.id_kerudung) WHERE id_transaksi = ?";
        Transaksi_krdg transaksi = jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(Transaksi_krdg.class), id_transaksi);
        Kerudung kerudung = jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(Kerudung.class), id_transaksi);
        model.addAttribute("transaksi_krdg", transaksi);
        model.addAttribute("kerudung", kerudung);
        return "detailtransaksikerudung";
    }
}