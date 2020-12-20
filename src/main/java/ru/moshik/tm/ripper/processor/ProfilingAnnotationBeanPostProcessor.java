package ru.moshik.tm.ripper.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.moshik.tm.ripper.annotations.Profiling;
import ru.moshik.tm.ripper.profiling.ProfilingController;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> beans = new HashMap<String, Class>();
    private ProfilingController controller = new ProfilingController();

    public ProfilingAnnotationBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Profiling.class)) {
            beans.put(beanName, bean.getClass());
        }
        return bean;
    }

    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        Class beanClass = beans.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                    if (controller.isEnabled()) {
                        System.out.println("Профилирую...");
                        long before = System.nanoTime();
                        Object retVal = method.invoke(bean, args);
                        long after = System.nanoTime();
                        System.out.println(after - before);
                        System.out.println("Закончил.");
                        return retVal;
                    } else return method.invoke(bean, args);
                }
            });
        }
        return bean;
    }
}
