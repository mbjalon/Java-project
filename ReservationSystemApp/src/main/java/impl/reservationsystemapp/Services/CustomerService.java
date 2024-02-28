package impl.reservationsystemapp.Services;

import impl.reservationsystemapp.Entities.Customer;
import impl.reservationsystemapp.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer with id " + id + " not found"));
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer customer = getCustomerById(id);
        customer.setName(updatedCustomer.getName());
        customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
